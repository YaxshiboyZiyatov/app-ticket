package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetByTimesEventSessionDto {
    private UUID eventSessionId;
    private UUID placeId;
    private Timestamp startTime;
    private Timestamp endTime;
}
