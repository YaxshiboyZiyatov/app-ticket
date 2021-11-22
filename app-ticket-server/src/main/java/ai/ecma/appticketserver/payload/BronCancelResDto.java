package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.Bron;
import ch.qos.logback.core.joran.spi.NoAutoStart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Set;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class BronCancelResDto {

    private String cardNumber;

    private long priceSumm;

}
