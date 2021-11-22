package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.enums.BronStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BronReqDto {
    @NotNull
    private UUID ticket;
    @NotNull
    private UUID user;
    @NotNull
    private Timestamp bronTime;
    @NotBlank
    private BronStatusEnum bronStatusEnum;
    @NotNull
    private Timestamp endTime;
}
