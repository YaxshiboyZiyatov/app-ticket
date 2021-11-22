package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TicketHistoryResDto {

    private UUID id;

    private UUID ticketId;

    private UUID userId; //yaxshilib ko'rib chiqish kk

    private String statusEnum;



}
