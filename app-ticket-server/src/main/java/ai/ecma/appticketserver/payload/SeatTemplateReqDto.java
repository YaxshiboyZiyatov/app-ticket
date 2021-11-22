package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatTemplateReqDto {
    private String name;
    private UUID photoId;
    private List<SeatChairDto> seatChairDtoList;
}
