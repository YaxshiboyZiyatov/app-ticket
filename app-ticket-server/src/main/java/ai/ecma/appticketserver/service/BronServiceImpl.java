package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.*;
import ai.ecma.appticketserver.enums.*;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static ai.ecma.appticketserver.enums.OrderTypeEnum.SOLD;

@Service
@RequiredArgsConstructor
public class BronServiceImpl implements BronService {

    private final BronRepository bronRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final PaymentReturnRepository paymentReturnRepository;
    private final PaymeService paymeService;
    private final BronReturnOrderRepository bronReturnOrderRepository;
    private final OrderRepository orderRepository;
    private final PayTypeRepository payTypeRepository;
    private final PaymentRepository paymentRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final TicketPaymentRepository ticketPaymentRepository;


    @Override
    public ApiResult<CustomPage<BronResDto>> getStatus(int page, int size, BronStatusEnum bronStatus) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bron> allByBronStatusEnum = bronRepository.findAllByBronStatusEnum(bronStatus, pageable);
        CustomPage<BronResDto> bronResDtoCustomPage = mapBronResDtoCustomPage(allByBronStatusEnum);
        return ApiResult.successResponse(bronResDtoCustomPage);
    }

    @Override
    public ApiResult<CustomPage<BronResDto>> getByUserId(int page, int size, User user) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Bron> byUserId = bronRepository.findAllByUserId(pageable, user.getId());
        CustomPage<BronResDto> bronResDtoCustomPage = mapBronResDtoCustomPage(byUserId);
        return ApiResult.successResponse(bronResDtoCustomPage);


    }

    @Override
    public ApiResult<CustomPage<BronResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Bron> all = bronRepository.findAll(pageable);
        CustomPage<BronResDto> bronResDtoCustomPage = mapBronResDtoCustomPage(all);
        return ApiResult.successResponse(bronResDtoCustomPage);
    }

    @Override
    public ApiResult<BronResDto> getById(UUID id) {
        Bron bron = bronRepository.findById(id).orElseThrow(() -> new RestException("Not found!", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(bronResDtoTo(bron));
    }


    @Override
    public ApiResult<BronResDto> add(User user, BronReqDto bronReqDto) {

//        Ticket ticket = ticketRepository.findById(bronReqDto.getTicket()).orElseThrow(() -> new RestException("Ticket not found!", HttpStatus.NOT_FOUND));
//
//        if (!ticket.getStatusEnum().equals(SeatStatusEnum.VACANT))
//            throw new RestException(ticket.getStatusEnum().nameUZ, HttpStatus.CONFLICT);
//
//        Bron bron = new Bron(
//                ticket,
//                user,
//                bronReqDto.getBronTime(),
//                bronReqDto.getBronStatusEnum(),
//                bronReqDto.getEndTime()
//        );
//
//        bronRepository.save(bron);
//        return ApiResult.successResponse("Success", bronResDtoTo(bron));

        return null;
    }


    @Override
    public ApiResult<BronCancelResDto> cancelBron(User user, BronCancelDto bronCancelDto) {

        //Agar client bronni cancel qilmoqchi bo'lsa

        List<Bron> bronList = null;
        if (!user.getRole().getPermission().contains(PermissionEnum.CANCEL_BRON)) {
            bronList = bronRepository.findAllByIdInAndUserId(bronCancelDto.getBronIdSet(), user.getId());
        } else {
            bronList = bronRepository.findAllById(bronCancelDto.getBronIdSet());
        }
        if (bronList.size() != bronCancelDto.getBronIdSet().size())
            throw new RestException("BAD_REQUEST", HttpStatus.BAD_REQUEST);
        /*
        bron vaqti tugamaganlarini "bronSet" ga yig'ib olamz.
        ticketlarni hammasi bron qilinganmi tekshiramz
        ticketlarga tolangan pullarni summasinni yigib olamz
        bornlarni setga yigib qaytaramz
        kn Click ga xabar jonatamz shu kartga shuncha pul  otkaz dep
        pul otgandan kn bronlarni ochirib biletlar yana sotuvga qoyiladi
        bronni ochirish un yana bitta yol yozaman

      paymentReturn ga ham malumotlarni yozib kettim.succes false qilip


         */

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Set<Bron> bronSet = new HashSet<>();
        long price = 0;
        Set<PaymentReturn> paymentReturnSet = new HashSet<>();


        String[] bronIdArray = new String[bronList.size()];
        for (int i = 0; i < bronList.size(); i++) {
            Bron bron = bronList.get(i);
            if (!bron.getTicket().getStatusEnum().equals(SeatStatusEnum.BOOKED))
                throw new RestException("Ticket avval booked qilinmagan", HttpStatus.CONFLICT);


            if (!bron.getEndTime().before(timestamp))
                throw new RestException("Bron vaqti tugagan", HttpStatus.BAD_REQUEST);

            price += bron.getTicketPayment().getAmount() / 2;
            bronIdArray[i] = bron.getId().toString();
//                bron.getTicket().setStatusEnum(SeatStatusEnum.PAYMENT_PROCESS);
            bronSet.add(bron);

            paymentReturnSet.add(new PaymentReturn(
                    bron.getTicketPayment().getAmount() / 2,
                    bron.getTicket(),
                    bronCancelDto.getCardNumber(),
                    false
            ));
        }
        BronReturnOrder bronReturnOrder = new BronReturnOrder(bronIdArray, bronCancelDto.getCardNumber(), price);
        bronReturnOrderRepository.save(bronReturnOrder);

        ApiResult<?> apiResult = paymeService.returnPaymentBron(price, bronCancelDto.getCardNumber(), bronReturnOrder.getId());
        if (!apiResult.isSuccess())
            throw new RestException("Server xatoligi,qayta urinib ko'ring.", HttpStatus.INTERNAL_SERVER_ERROR);
        paymentReturnRepository.saveAll(paymentReturnSet);
        bronRepository.saveAll(bronSet);
        return ApiResult.successResponse("Ariza qabul qilindi");
    }


    @Override
    public ApiResult<BronResDto> edit(UUID id, BronReqDto bronReqDto) {
        Bron bron = bronRepository.findById(id).orElseThrow(() -> new RestException("Bron not found", HttpStatus.NOT_FOUND));

        Ticket ticket = ticketRepository.findById(bronReqDto.getTicket()).orElseThrow(() -> new RestException("Ticket not found!", HttpStatus.NOT_FOUND));

        if (ticket.getStatusEnum().equals(SeatStatusEnum.BOOKED)
                || ticket.getStatusEnum().equals(SeatStatusEnum.SOLD)
                || ticket.getStatusEnum().equals(SeatStatusEnum.RESERVED)
                || ticket.getStatusEnum().equals(SeatStatusEnum.VIP)
        ) {
            throw new RestException(ticket.getStatusEnum().nameUZ, HttpStatus.CONFLICT);
        }

        User user = userRepository.findById(bronReqDto.getUser()).orElseThrow(() -> new RestException("User ro'yxatdan o'tmagan!", HttpStatus.CONFLICT));

        if (!user.isEnabled())
            throw new RestException("User not active", HttpStatus.CONFLICT);

        if (!user.getRole().getRoleType().equals(RoleTypeEnum.CLIENT))
            throw new RestException("Bron qilish faqat mijozlar uchun", HttpStatus.CONFLICT);

        bron.setTicket(ticket);
        bron.setUser(user);
        bron.setBronTime(bronReqDto.getBronTime());
        bron.setBronStatusEnum(bronReqDto.getBronStatusEnum());
        bron.setEndTime(bronReqDto.getEndTime());
        bronRepository.save(bron);
        return ApiResult.successResponse("Success editing!", bronResDtoTo(bron));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            bronRepository.deleteById(id);
            return ApiResult.successResponse("Success deleting!");
        } catch (Exception e) {
            throw new RestException("Not found!", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ApiResult<?> bookedTicketBuy(User user, TicketBuyDto ticketBuyDto) {

        List<UUID> ticketIdList = ticketBuyDto.getTicketIdList();
        List<Ticket> ticketList = ticketRepository.findAllById(ticketIdList);
        if (ticketList.isEmpty())
            throw new RestException("Ticketlar bosh bolmasin,yoki ticket band qilinmagan", HttpStatus.BAD_REQUEST);

        User userTickets = userRepository.findById(ticketBuyDto.getUserId())
                .orElseThrow(() -> new RestException("User not found", HttpStatus.NOT_FOUND));
        List<Bron> bronList = bronRepository.findAllByTicketIdInAndUser(ticketBuyDto.getTicketIdList(), userTickets);

        if (ticketList.size() != ticketIdList.size())
            throw new RestException("Ticketlarini topishda xatolik", HttpStatus.CONFLICT);
        if (bronList.size() != ticketList.size())
            throw new RestException("Qaysidir ticket band qilinmagan", HttpStatus.CONFLICT);

        double price = 0;
        for (Ticket ticket : ticketList) {
            if (!ticket.getStatusEnum().equals(SeatStatusEnum.BOOKED))
                throw new RestException("Ticket band qilinmagan", HttpStatus.BAD_REQUEST);

            price += ticket.getPrice();

        }
        double amount = 0;
        for (Bron bron : bronList) {
            if (!bron.getBronStatusEnum().equals(BronStatusEnum.ACTIVE))
                throw new RestException("Bron vaqti tugagan", HttpStatus.CONFLICT);

            amount += bron.getTicketPayment().getAmount();

        }
        Order order = new Order(
                userTickets,
                ticketIdList.stream().map(UUID::toString).toArray(String[]::new),
                (price - amount),
                SOLD,
                false
        );
        orderRepository.save(order);

        return ApiResult.successResponse(new OrderPayDto(order.getId(), order.getPrice()));
    }

    public ApiResult<?> ticketBuy(User user, OrderPayDto orderPayDto) {
        UUID orderId = orderPayDto.getOrderId();
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new RestException("Order not fount", HttpStatus.NOT_FOUND));

        Set<UUID> ticketIdSet = Arrays.stream(order.getTicketsId()).map(UUID::fromString).collect(Collectors.toSet());

        List<Ticket> ticketList = ticketRepository.findAllByUserIdAndIdIn(user.getId(), ticketIdSet);

        List<Bron> bronList = bronRepository.findAllByTicketIdIn(ticketIdSet);

        List<Ticket> tickets = new ArrayList<>();
        List<Bron> brons = new ArrayList<>();
        List<TicketHistory> ticketHistorys = new ArrayList<>();
        List<TicketPayment> ticketPayments = new ArrayList<>();
        PayType payType = payTypeRepository.findById(orderPayDto.getPayTypeId()).orElseThrow(() -> new RestException("Pay type not fount", HttpStatus.NOT_FOUND));

        Payment payment = new Payment(
                order.getPrice(),
                payType,
                ticketList.get(0).getUser()
        );
        for (Ticket ticket : ticketList) {
            if (!ticket.getStatusEnum().equals(SeatStatusEnum.BOOKED)) {
                throw new RestException("Ticket band qilinmagan", HttpStatus.BAD_REQUEST);
            }

            ticket.setStatusEnum(SeatStatusEnum.SOLD);
            ticket.setPayment(payment);
            tickets.add(ticket);

            ticketHistorys.add(new TicketHistory(ticket, ticket.getUser(), SeatStatusEnum.SOLD));
        }

        for (Bron bron : bronList) {
            if (!bron.getBronStatusEnum().equals(BronStatusEnum.ACTIVE))
                throw new RestException("Bron vaqti tugagan", HttpStatus.CONFLICT);

            Double price = bron.getTicket().getPrice();
            double amount = bron.getTicketPayment().getAmount();
            ticketPayments.add(new TicketPayment(
                    (price - amount),
                    payment,
                    bron.getTicket(),
                    SOLD
            ));
            bron.setBronStatusEnum(BronStatusEnum.COMPLETED);
            brons.add(bron);
        }
        order.setFinished(true);

        orderRepository.save(order);
        paymentRepository.save(payment);
        ticketPaymentRepository.saveAll(ticketPayments);
        ticketRepository.saveAll(tickets);
        bronRepository.saveAll(brons);
        ticketHistoryRepository.saveAll(ticketHistorys);

        return ApiResult.successResponse("ok");
    }

    public BronResDto bronResDtoTo(Bron bron) {
        return new BronResDto(
                bron.getId(),
                !Objects.isNull(bron.getTicket()) ? bron.getTicket().getId() : null,
                !Objects.isNull(bron.getUser()) ? bron.getUser().getId() : null,
                bron.getBronTime(),
                bron.getBronStatusEnum(),
                bron.getEndTime()
        );
    }


    private CustomPage<BronResDto> mapBronResDtoCustomPage(Page<Bron> bronPage) {
        return new CustomPage<>(
                bronPage.getContent()
                        .stream()
                        .map(this::bronResDtoTo)
                        .collect(Collectors.toList()),
                bronPage.getNumberOfElements(),
                bronPage.getNumber(),
                bronPage.getTotalElements(),
                bronPage.getTotalPages(),
                bronPage.getSize()
        );
    }

}
