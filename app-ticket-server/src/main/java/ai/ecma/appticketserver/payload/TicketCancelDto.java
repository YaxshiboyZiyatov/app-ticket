package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketCancelDto {

    @NotEmpty(message = "Ticketlarni id si bo'sh bolmasin")
    @NotNull(message = "bla bla")
    private List<UUID> ticketIdList;

    @Size(min = 16,max = 16,message = "Card number xato kiritilgan")
    private String cardNumber;

}