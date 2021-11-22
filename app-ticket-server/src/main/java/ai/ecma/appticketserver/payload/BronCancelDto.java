package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BronCancelDto {
    @NotNull
    @NotEmpty
    private Set<UUID> bronIdSet;
    @Size(min = 16,max = 16)
    private String cardNumber;
}
