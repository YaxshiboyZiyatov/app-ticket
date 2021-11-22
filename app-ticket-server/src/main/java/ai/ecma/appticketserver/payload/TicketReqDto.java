package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketReqDto {

    private String seatName;

    private UUID userId;

    private UUID eventSessionId;

    private SeatStatusEnum seatStatusEnum;

    private int row;

    private String section;

    private Double price;

}
