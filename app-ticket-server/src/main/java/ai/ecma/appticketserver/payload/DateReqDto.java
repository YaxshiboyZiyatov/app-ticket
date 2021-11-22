package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateReqDto {

    @NotEmpty(message = "Date bo'sh bo'lmasin")
    private Timestamp timestampOne;

    @NotEmpty(message = "Date bo'sh bo'lmasin")
    private Timestamp timestampTwo;


//    private UUID eventId;

}
