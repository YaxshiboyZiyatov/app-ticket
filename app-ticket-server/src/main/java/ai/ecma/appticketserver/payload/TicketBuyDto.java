package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketBuyDto {

    private double price;

    private UUID userId;

    private List<UUID> ticketIdList;

//    private String cardNumber;

}
