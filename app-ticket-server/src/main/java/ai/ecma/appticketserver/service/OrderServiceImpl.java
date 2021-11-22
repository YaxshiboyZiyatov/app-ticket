package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.*;
import ai.ecma.appticketserver.enums.BronStatusEnum;
import ai.ecma.appticketserver.enums.OrderTypeEnum;
import ai.ecma.appticketserver.enums.PayTypeEnum;
import ai.ecma.appticketserver.enums.SeatStatusEnum;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.*;
import ai.ecma.appticketserver.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final BronRepository bronRepository;
    private final PaymentRepository paymentRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final PayTypeRepository payTypeRepository;

    @Override
    public ApiResult<CustomPage<OrderResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orderPage = orderRepository.findAll(pageable);
        CustomPage<OrderResDto> orderResDtoCustomPage = mapEventOrderResDtoCustomPage(orderPage);
        return ApiResult.successResponse(orderResDtoCustomPage);
    }

    @Override
    public ApiResult<OrderResDto> getById(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RestException("order not found", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(Mapper.orderResDto(order));
    }

    @Override
    public ApiResult<OrderPayDto> add(User user, OrderReqDto orderReqDto) {
        Order order = new Order();
        order.setUser(user);

        List<Ticket> ticketList = ticketRepository.findAllById(orderReqDto.getTicketIdList());

        double totalPrice = 0.0;
        String[] orderTicketIds = new String[ticketList.size()];

        for (int i = 0; i < ticketList.size(); i++) {
            Ticket ticket = ticketList.get(i);
            if (!ticket.getStatusEnum().equals(SeatStatusEnum.VACANT))
                throw new RestException("Ticket already sold", HttpStatus.BAD_REQUEST);
            ticket.setStatusEnum(SeatStatusEnum.PAYMENT_PROCESS);
            totalPrice += calculatePrice(ticket, orderReqDto.getOrderType());
            orderTicketIds[i] = ticket.getId().toString();
        }
        order.setOrderType(orderReqDto.getOrderType());
        order.setPrice(totalPrice);
        order.setTicketsId(orderTicketIds);
        ticketRepository.saveAll(ticketList);
        orderRepository.save(order);
        return ApiResult.successResponse(new OrderPayDto(order.getId(), order.getPrice()));
    }

    @Override
    public ApiResult<?> checkTicket(CheckOrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.getOrderId()).orElseThrow(() -> new RestException("Order not found!", HttpStatus.NOT_FOUND));

        if (order.getFinished() || order.getPrice() != orderDto.getPrice())
            throw new RestException("Order not found", HttpStatus.NOT_FOUND);

        Set<UUID> ticketIds = Arrays.stream(order.getTicketsId()).map(UUID::fromString).collect(Collectors.toSet());

        List<Ticket> allTickets = ticketRepository.findAllById(ticketIds);

        if (allTickets.stream().anyMatch(ticket -> !ticket.getStatusEnum().equals(SeatStatusEnum.PAYMENT_PROCESS)))
            throw new RestException("Orderning muddati o'tib ketibdi", HttpStatus.CONFLICT);

        return ApiResult.successResponse();
    }

    @Override
    public ApiResult<?> finish(User currentUser, CheckOrderDto orderDto) {
        Order order = orderRepository.findById(orderDto.getOrderId()).orElseThrow(() -> new RestException("Order not found!", HttpStatus.NOT_FOUND));

        if (order.getFinished())
            throw new RestException("Order already completed!", HttpStatus.CONFLICT);

        Set<UUID> ticketIds = Arrays.stream(order.getTicketsId()).map(UUID::fromString).collect(Collectors.toSet());

        List<Ticket> allTickets = ticketRepository.findAllById(ticketIds);

        List<Bron> bronList = new ArrayList<>();
        List<TicketPayment> ticketPaymentList = new ArrayList<>();
        List<TicketHistory> ticketHistoryList = new ArrayList<>();
        Payment payment = new Payment();

        for (int i = 0; i < allTickets.size(); i++) {
            Ticket ticket = allTickets.get(i);
            if (!ticket.getStatusEnum().equals(SeatStatusEnum.PAYMENT_PROCESS))
                throw new RestException("Orderning muddati o'tib ketibdi", HttpStatus.CONFLICT);

            ticket.setUser(order.getUser());
            ticket.setPayment(payment);
            TicketPayment ticketPayment = new TicketPayment(ticket.getPrice(), payment, ticket, order.getOrderType());
            ticketPaymentList.add(ticketPayment);
            ticket.setUser(order.getUser());

            if (order.getOrderType().equals(OrderTypeEnum.SOLD)) {
                ticketHistoryList.add(new TicketHistory(
                        ticket,
                        order.getUser(),
                        SeatStatusEnum.SOLD
                ));

                ticket.setStatusEnum(SeatStatusEnum.SOLD);
            } else {
                ticketHistoryList.add(new TicketHistory(
                        ticket,
                        order.getUser(),
                        SeatStatusEnum.BOOKED
                ));
                ticket.setStatusEnum(SeatStatusEnum.BOOKED);
                BronTariff bronTariff = ticket.getEventSession().getBronTariff();
                ticketPayment.setAmount(bronTariff.getPercent() * ticket.getPrice());
                Bron bron = new Bron(
                        ticket,
                        order.getUser(),
                        new Timestamp(System.currentTimeMillis()),
                        BronStatusEnum.ACTIVE,
                        new Timestamp(System.currentTimeMillis() + ((long) bronTariff.getLifetime() * 60 * 1000)),
                        ticketPayment
                );
                bronList.add(bron);
            }
        }
        PayType payType = payTypeRepository.findByNameAndPayTypeEnum(currentUser.getRole().getName(), PayTypeEnum.CARD)
                .orElseThrow(() -> new RestException("Pay type not found", HttpStatus.NOT_FOUND));
        payment.setTicketPaymentList(ticketPaymentList);
        payment.setAmount(order.getPrice());
        payment.setUser(order.getUser());
        payment.setPayType(payType);

        order.setFinished(true);

        paymentRepository.save(payment);
        ticketRepository.saveAll(allTickets);
        ticketHistoryRepository.saveAll(ticketHistoryList);
        if (!bronList.isEmpty())
            bronRepository.saveAll(bronList);
        orderRepository.save(order);

        return ApiResult.successResponse("Payment success");
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            orderRepository.deleteById(id);
            return ApiResult.successResponse(id + " - order deleted");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RestException("order not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private CustomPage<OrderResDto> mapEventOrderResDtoCustomPage(Page<Order> orderPage) {
        return new CustomPage<>(
                orderPage.getContent()
                        .stream()
                        .map(Mapper::orderResDto)
                        .collect(Collectors.toList()),
                orderPage.getNumberOfElements(),
                orderPage.getNumber(),
                orderPage.getTotalElements(),
                orderPage.getTotalPages(),
                orderPage.getSize()
        );
    }

    private Double calculatePrice(Ticket ticket, OrderTypeEnum orderType) {
        Double price;
        if (orderType.equals(OrderTypeEnum.BRON)) {
            BronTariff bronTariff = ticket.getEventSession().getBronTariff();
            double percent = bronTariff.getPercent();
            price = ticket.getPrice() * (percent / 100);
        } else {
            price = ticket.getPrice();
        }
        return price;
    }
}
