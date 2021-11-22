package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReturnResDto {
    private UUID id;

    private double amount;

    private Ticket ticketId;

    private String CardNumber;

    private boolean success;
}
