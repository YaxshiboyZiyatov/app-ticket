package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.enums.OrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderReqDto {
    @NotNull
    @NotEmpty
    private List<UUID> ticketIdList;

    @NotNull
    private OrderTypeEnum orderType;
}
