package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BronTariffReqDto {

    private Integer lifetime;

    private Integer percent;

    @NotNull
    private boolean disabled;

}
