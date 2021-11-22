package ai.ecma.appticketserver.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class TicketHistoryReqDto {

    private UUID ticketId;

    private UUID userId;
}
