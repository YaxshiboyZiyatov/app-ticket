package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.PayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentReqDto {

    @NotNull
    private double amount;

    @NotBlank
    private PayType payType;

    @NotNull
    private UUID user;

}
