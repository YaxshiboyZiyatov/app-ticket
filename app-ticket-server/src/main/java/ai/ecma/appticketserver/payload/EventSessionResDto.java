package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventSessionResDto {
    private UUID id;

    private EventResDto event;

    private Timestamp startTime;

    private Timestamp endTime;

    private BronTariffResDto bronTariff;

}
