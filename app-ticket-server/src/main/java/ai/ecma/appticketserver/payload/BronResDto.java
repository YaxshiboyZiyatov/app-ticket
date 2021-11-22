package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.Ticket;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.BronStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BronResDto {
    @NotNull
    private UUID id;
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
