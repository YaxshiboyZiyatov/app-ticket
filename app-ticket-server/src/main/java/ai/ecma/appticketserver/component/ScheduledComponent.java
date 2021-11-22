package ai.ecma.appticketserver.component;

import ai.ecma.appticketserver.entity.Order;
import ai.ecma.appticketserver.enums.SeatStatusEnum;
import ai.ecma.appticketserver.repository.BronRepository;
import ai.ecma.appticketserver.repository.OrderRepository;
import ai.ecma.appticketserver.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@EnableScheduling
@Component
@RequiredArgsConstructor
public class ScheduledComponent {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final BronRepository bronRepository;

    @Value("${orderLifeTime}")
    private long orderLifeTime;

    @Scheduled(fixedRate = 30_000)
    public void orderRemover() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis() - orderLifeTime);
        List<Order> orderList = orderRepository.findAllByCreatedAtBeforeAndFinishedFalse(timestamp);
        List<UUID> ticketsId = new ArrayList<>();
        for (Order order : orderList) {
            for (String id : order.getTicketsId()) {
                ticketsId.add(UUID.fromString(id));
            }
        }
        if (!ticketsId.isEmpty())
            ticketRepository.changeVacantOldTickets(ticketsId, SeatStatusEnum.VACANT.name());
    }


//    @Scheduled(fixedRate = 30_000)
//    public void bronRemover(){
//        List<Bron> bronList = bronRepository.findAllByEndTimeBeforeAndBronStatusEnumNot(
//                new Timestamp(System.currentTimeMillis()),
//                BronStatusEnum.COMPLETED
//        );
//        bronList.forEach(bron -> bron.setBronStatusEnum(BronStatusEnum.AUTO_CANCELED));
//        bronRepository.saveAll(bronList);
//    }
}
