package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BronReturnOrderDto {

    @NotBlank(message = "id bo'sh bo'lmasin")
    private UUID bronReturnOrderId;

    @Size(min = 16,max = 16,message = "Card number xato kiritilgan")
    private String cardNumber;

    @Positive(message = "narx musbat bo'lishi kerak")
    private double price;

}
