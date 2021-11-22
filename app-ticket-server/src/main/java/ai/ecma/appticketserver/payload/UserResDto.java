package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private RoleResDto roleResDto;


    public UserResDto(UUID id, String firstName, String lastName, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }



}
