package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatTemplateChairResDto {
    private UUID id;
    private SeatStatusEnum seatStatusEnum;
    private String nameSeat;
    private double price;
    private int row;
    private String section;
    private SeatTemplateResDto seatTemplateResDto;
}
