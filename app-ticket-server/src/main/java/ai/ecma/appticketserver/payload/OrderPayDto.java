package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.PayType;
import ai.ecma.appticketserver.enums.PayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPayDto {
    private UUID orderId;
    private double price;
    private String cardNumber;

    private UUID payTypeId;

    public OrderPayDto(UUID orderId, double price) {
        this.orderId = orderId;
        this.price = price;
    }
}
