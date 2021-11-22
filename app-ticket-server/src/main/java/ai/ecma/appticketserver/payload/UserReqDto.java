package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReqDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private UUID roleId;
    private String password;
    private boolean enabled;
}
