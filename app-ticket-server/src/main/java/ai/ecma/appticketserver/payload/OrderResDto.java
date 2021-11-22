package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderResDto {
    private UUID id;

    private UserResDto userResDto;

    private double price;

    private Boolean finished;
}
