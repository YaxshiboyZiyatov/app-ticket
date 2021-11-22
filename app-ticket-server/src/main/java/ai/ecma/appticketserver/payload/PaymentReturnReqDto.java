package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReturnReqDto {
    @NotNull
    private double amount;
    @NotNull
    private UUID ticketId;
    @NotBlank
    private String CardNumber;

    private boolean success;
}
