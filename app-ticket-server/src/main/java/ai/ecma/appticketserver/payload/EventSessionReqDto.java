package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.BronTariff;
import ai.ecma.appticketserver.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventSessionReqDto {
    @NotNull(message = "Bla bla kelmadi")
    private UUID eventId;
    @NotNull(message = "Bla bla kelmadi")
    private Timestamp startTime;
    @NotNull(message = "Bla bla kelmadi")
    private Timestamp endTime;

    private UUID bronTariffId;
}
