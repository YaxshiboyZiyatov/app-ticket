package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.enums.PayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PayTypeReqDto {
    private String name;

    private PayTypeEnum payTypeEnum;
}
