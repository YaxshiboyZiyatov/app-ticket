package ai.ecma.appticketserver.payload;

import lombok.Data;

import java.util.UUID;

@Data
public class TicketBuyReqDto {

    private UUID ticketId;

    private UUID userId;

    private UUID eventSessionId;
}
