package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.enums.OrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Muhammad Mo'minov
 * 30.09.2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CheckOrderDto {
    @NotNull
    private UUID orderId;

    @NotNull
    private Double price;

    @NotBlank
    private String merchantId;

    @NotNull
    private OrderTypeEnum orderType;
}
