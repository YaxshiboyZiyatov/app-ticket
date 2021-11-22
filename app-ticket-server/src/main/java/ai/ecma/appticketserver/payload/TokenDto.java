package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenDto {
    @NotBlank(message = "Access token required")
    private String accessToken;

    @NotBlank(message = "Refresh token required")
    private String refreshToken;
}
