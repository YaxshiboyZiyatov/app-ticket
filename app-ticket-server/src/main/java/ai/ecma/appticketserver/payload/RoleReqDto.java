package ai.ecma.appticketserver.payload;

import ai.ecma.appticketserver.enums.PermissionEnum;
import ai.ecma.appticketserver.enums.RoleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoleReqDto {


    private String name;

    private String description;

    private RoleTypeEnum roleType;

    private Set<PermissionEnum> permission;

}
