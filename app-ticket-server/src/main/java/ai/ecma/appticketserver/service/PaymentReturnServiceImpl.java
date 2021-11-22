package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.*;
import ai.ecma.appticketserver.enums.BronStatusEnum;
import ai.ecma.appticketserver.enums.OrderTypeEnum;
import ai.ecma.appticketserver.enums.SeatStatusEnum;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.*;
import ai.ecma.appticketserver.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentReturnServiceImpl implements PaymentReturnService {

    private final PaymentReturnRepository paymentReturnRepository;
    private final BronRepository bronRepository;
    private final BronReturnOrderRepository bronReturnOrderRepository;
    private final TicketRepository ticketRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final TicketReturnOrderRepository ticketReturnOrderRepository;
    private final TicketReturnTariffRepository ticketReturnTariffRepository;
    private final PaymeService paymeService;
    private final PayBackInfoRepository payBackInfoRepository;
    private final TicketPaymentRepository ticketPaymentRepository;
    private final PaymentRepository paymentRepository;


    @Value("${paymeMinSumm}")
    private double paymeMinSumm;


    @Override
    public ApiResult<CustomPage<PaymentReturnResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PaymentReturn> all = paymentReturnRepository.findAll(pageable);
        CustomPage<PaymentReturnResDto> map = map(all);
        return ApiResult.successResponse(map);
    }

    @Override
    public ApiResult<PaymentReturnResDto> get(UUID id) {
        PaymentReturn paymentReturn = paymentReturnRepository.findById(id).orElseThrow(() -> new RestException("Payment Return Not found", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(Mapper.paymentReturnResDto(paymentReturn));
    }

    @Override
    public ApiResult<CustomPage<PaymentReturnResDto>> returnPaymentByEventSum(UUID eventId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PaymentReturn> allByEventHistory = paymentReturnRepository.findAllByEventHistory(eventId, pageable);
        CustomPage<PaymentReturnResDto> map = map(allByEventHistory);
        double maxEventSum = paymentReturnRepository.getMaxEventSum(eventId);
        return ApiResult.successResponse("Umimiy qaytarilgan pul: " + maxEventSum, map);
    }

    @Override
    public ApiResult<?> cancelBron(BronReturnOrderDto bronReturnOrderDto) {

        UUID bronReturnOrderId = bronReturnOrderDto.getBronReturnOrderId();
        BronReturnOrder bronReturnOrder = bronReturnOrderRepository.findById(bronReturnOrderId).orElseThrow(() -> new RestException("BronReturnOrder topilmadi", HttpStatus.NOT_FOUND));
        String[] bronIdArray = bronReturnOrder.getBronIdList();
        Set<UUID> bronIdSet = Arrays.stream(bronIdArray).map(UUID::fromString).collect(Collectors.toSet());
        List<Bron> bronList = bronRepository.findAllById(bronIdSet);

        if (bronList.size() != bronIdArray.length
                || bronReturnOrder.getPrice() != bronReturnOrderDto.getPrice()
                || !bronReturnOrder.getCardNumber().equals(bronReturnOrderDto.getCardNumber()))
            throw new RestException("BAD_REQUEST", HttpStatus.BAD_REQUEST);

        Set<PaymentReturn> paymentReturnSet = new HashSet<>();
        Set<Ticket> ticketSet = new HashSet<>();
        Set<TicketHistory> ticketHistorySet = new HashSet<>();
        Set<PayBackInfo> payBackInfoSet = new HashSet<>();
        for (Bron bron : bronList) {
            if (!bron.getTicket().getStatusEnum().equals(SeatStatusEnum.BOOKED))
                throw new RestException("Ticket avval booked qilinmagan", HttpStatus.CONFLICT);

            Ticket ticket = bron.getTicket();
            ticket.setStatusEnum(SeatStatusEnum.VACANT);
            ticketSet.add(ticket);

            TicketHistory ticketHistory = new TicketHistory(
                    ticket,
                    bron.getUser(),
                    SeatStatusEnum.VACANT
            );
            ticketHistorySet.add(ticketHistory);
            payBackInfoSet.add(new PayBackInfo(
                    bron.getTicketPayment().getAmount() / 2,
                    bron.getTicketPayment(),
                    0,
                    bron.getTicketPayment().getAmount() / 2
            ));

            PaymentReturn paymentReturn = paymentReturnRepository.findByTicketId(bron.getTicket().getId()).
                    orElseThrow(() -> new RestException("PaymentReturn topilmadi,Avval Bron servicega cancel-brom yoliga boring", HttpStatus.CONFLICT));
            paymentReturn.setSuccessReturn(true);
            paymentReturnSet.add(paymentReturn);
        }

        payBackInfoRepository.saveAll(payBackInfoSet);
        ticketHistoryRepository.saveAll(ticketHistorySet);
        ticketRepository.saveAll(ticketSet);
        paymentReturnRepository.saveAll(paymentReturnSet);
        return ApiResult.successResponse(ApiResult.successResponse("payment Return saqlandi"));
    }

    /*
    bu method ticketni sotib olib uni atmen qilganda pulini qaytarib berayotganda ishlaydi.
    payme click...
     */
    @Override
    public ApiResult<?> ticketPaymentReturn(TicketReturnOrderDto ticketReturnOrderDto) {

        TicketReturnOrder ticketreturnOrder = ticketReturnOrderRepository.findById(ticketReturnOrderDto.getTicketReturnOrderId()).orElseThrow(() -> new RestException("TicketReturnOrder topilmadi", HttpStatus.NOT_FOUND));
        String[] ticketIdList = ticketreturnOrder.getTicketIdList();
        Set<UUID> ticketIdSet = Arrays.stream(ticketIdList).map(UUID::fromString).collect(Collectors.toSet());
        List<Ticket> ticketList = ticketRepository.findAllById(ticketIdSet);


        if (ticketReturnOrderDto.getPrice() != ticketreturnOrder.getPrice()
                || !ticketReturnOrderDto.getCardNumber().equals(ticketreturnOrder.getCardNumber()))
            throw new RestException("CardNumber va price da xatolik bor", HttpStatus.BAD_REQUEST);

        if (ticketList.isEmpty())
            throw new RestException("Ticket not present", HttpStatus.NOT_FOUND);

        Set<PayBackInfo> payBackInfoSet = new HashSet<>();
        List<TicketHistory> ticketHistoryList = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Ticket ticket : ticketList) {

            Timestamp startTime = ticket.getEventSession().getStartTime();
            LocalDateTime localDateTime = startTime.toLocalDateTime();
            int duration = (int) Duration.between(localDateTime, now).toMinutes();

            Optional<TicketReturnTariff> optionalTicketReturnTariff = ticketReturnTariffRepository.getTicketReturnTariffId(ticket.getId(), duration);
            if (optionalTicketReturnTariff.isPresent()) {
                TicketReturnTariff ticketReturnTariff = optionalTicketReturnTariff.get();
                Optional<Bron> optionalBron = bronRepository.findFirstByTicketAndBronStatusEnumAndUserOrderByCreatedAtDesc(ticket,BronStatusEnum.COMPLETED, ticket.getUser());
                if (optionalBron.isPresent()) {
                    Bron bron = optionalBron.get();
                    bron.setBronStatusEnum(BronStatusEnum.CANCELED);
                    bronRepository.save(bron);
                    payBackInfoSet.add(new PayBackInfo(
                            bron.getTicketPayment().getAmount() * (ticketReturnTariff.getReturnPercent() / 100),
                            bron.getTicketPayment(),
                            0,
                            bron.getTicketPayment().getAmount() * (1 - ticketReturnTariff.getReturnPercent() / 100)
                    ));
                }
//                ticketPaymentId = (ticketPaymentId == null) ? UUID.randomUUID() : ticketPaymentId;
//                Optional<TicketPayment> optionalTicketPayment = ticketPaymentRepository.findByIdNotAndTicketAndPayment_User(ticketPaymentId, ticket, ticket.getUser());
                Optional<TicketPayment> optionalTicketPayment =
                        ticketPaymentRepository.findFirstByTicketAndPayment_UserAndReturnedFalseAndOrderTypeEnumOrderByCreatedAtDesc(ticket, ticket.getUser(), OrderTypeEnum.SOLD);
                if (optionalTicketPayment.isPresent()) {
                    TicketPayment ticketPayment = optionalTicketPayment.get();
                    ticketPayment.setReturned(true);
                    ticketPaymentRepository.save(ticketPayment);
                    payBackInfoSet.add(new PayBackInfo(
                                    ticketPayment.getAmount() * ticketReturnTariff.getReturnPercent() / 100,
                                    ticketPayment,
                                    0,
                                    ticketPayment.getAmount() * (1 - ticketReturnTariff.getReturnPercent() / 100)
                            )
                    );
                }
            }
            ticketHistoryList.add(new TicketHistory(
                    ticket,
                    ticket.getUser(),
                    SeatStatusEnum.VACANT
            ));
        }

        payBackInfoRepository.saveAll(payBackInfoSet);
        paymentReturnRepository.completePaymentReturnByOrder(ticketReturnOrderDto.getTicketReturnOrderId());
//        List<PaymentReturn> paymentReturn = paymentReturnRepository.findAllByTicketIdInAndSuccessReturnFalse(ticketIdSet);
//        paymentReturn.forEach(paymentReturn1 -> paymentReturn1.setSuccessReturn(true));
//        paymentReturnRepository.saveAll(paymentReturn);
        ticketRepository.changeVacantOldTickets(new ArrayList<>(ticketIdSet), SeatStatusEnum.VACANT.name());
//        List<Ticket> ticketStatus = ticketRepository.findAllByIdInAndUser(ticketIdSet, ticketList.get(0).getUser());
//        ticketStatus.forEach(ticket -> ticket.setStatusEnum(SeatStatusEnum.VACANT));
//        ticketRepository.saveAll(ticketStatus);
        ticketHistoryRepository.saveAll(ticketHistoryList);
        return ApiResult.successResponse("ok");
    }

    public ApiResult<?> cancelTickets(TicketCancelDto ticketCancelDto, User user) {

        List<Ticket> ticketList = ticketRepository.findAllById(ticketCancelDto.getTicketIdList());
        if (ticketList.isEmpty())
            throw new RestException("Ticket not present", HttpStatus.NOT_FOUND);

        double price = 0;
        Ticket ticket = null;
        LocalDateTime now = LocalDateTime.now();
        List<PaymentReturn> paymentReturnList = new ArrayList<>();
        UUID orderId = UUID.randomUUID();
        for (int i = 0; i < ticketList.size(); i++) {
            ticket = ticketList.get(i);

            if (!ticket.getStatusEnum().equals(SeatStatusEnum.SOLD))
                throw new RestException("Bu ticket hali sotilmagan!", HttpStatus.BAD_REQUEST);

            Timestamp startTime = ticket.getEventSession().getStartTime();
            LocalDateTime localDateTime = startTime.toLocalDateTime();
            int duration = (int) Duration.between(localDateTime, now).toMinutes();

            TicketReturnTariff ticketReturnTariff = ticketReturnTariffRepository.getTicketReturnTariffId(ticket.getId(), duration).orElse(new TicketReturnTariff());
            if (ticketReturnTariff == null) {
                ticketRepository.changeVacantOldTickets(new ArrayList<>(ticketCancelDto.getTicketIdList()), SeatStatusEnum.VACANT.name());
                List<TicketHistory> ticketHistoryList = new ArrayList<>();
                ticketHistoryList.add(new TicketHistory(
                        ticket,
                        ticket.getUser(),
                        SeatStatusEnum.VACANT
                ));
                ticketHistoryRepository.saveAll(ticketHistoryList);
                return ApiResult.successResponse("Ticketlar muvaffaqiyatli qaytarildi!");
            }
            double priceReturn = Math.ceil(ticket.getPrice() * (ticketReturnTariff.getReturnPercent() / 100));
            price += priceReturn;

            paymentReturnList.add(new PaymentReturn(
                    priceReturn,
                    ticket,
                    ticketCancelDto.getCardNumber(),
                    false,
                    orderId
            ));
        }
        TicketReturnOrder ticketReturnOrder = new TicketReturnOrder(
                ticketCancelDto.getTicketIdList().stream().map(UUID::toString).toArray(String[]::new),
                ticketCancelDto.getCardNumber(),
                price
        );
        ticketReturnOrder.setId(orderId);

//        if (price < paymeMinSumm)
//            throw new RestException("Minumal summadan kam bo'lgan pul qaytalolmaysiz", HttpStatus.BAD_REQUEST);

        ticketReturnOrderRepository.save(ticketReturnOrder);
        paymentReturnRepository.saveAll(paymentReturnList);

        paymeService.returnPaymentTicket(price, ticketCancelDto.getCardNumber(), ticketReturnOrder.getId());

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<PaymentReportResDto> reportCancel(DateReqDto dateReqDto) {
        List<PaymentReturn> returnList = paymentReturnRepository.findAllByCreatedAtBetweenAndSuccessReturnTrue(dateReqDto.getTimestampOne(), dateReqDto.getTimestampTwo());
        double summAmount = 0;
        for (PaymentReturn paymentReturn : returnList) {
            summAmount += paymentReturn.getAmount();
        }

        return ApiResult.successResponse();
//        return ApiResult.successResponse(new PaymentReportResDto(
//                "Klientlarga qaytarib berilgan umumiy pul miqdori",
//                summAmount
//        ));
    }

    private CustomPage<PaymentReturnResDto> map(Page<PaymentReturn> paymentReturns) {
        CustomPage<PaymentReturnResDto> customPage = new CustomPage<>(
                paymentReturns.getContent()
                        .stream()
                        .map(Mapper::paymentReturnResDto)
                        .collect(Collectors.toList()),
                paymentReturns.getNumberOfElements(),
                paymentReturns.getNumber(),
                paymentReturns.getTotalElements(),
                paymentReturns.getTotalPages(),
                paymentReturns.getSize()
        );
        return customPage;
    }

}
