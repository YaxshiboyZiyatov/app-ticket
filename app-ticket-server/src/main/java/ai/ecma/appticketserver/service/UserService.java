package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Ticket;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.RoleTypeEnum;
import ai.ecma.appticketserver.payload.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public interface UserService {
    ApiResult<?> changePassword(User currentUser, ChangePasswordDto changePasswordDto);

    ApiResult<UserResDto> get(UUID id);

    ApiResult<CustomPage<UserResDto>> getAll(int page, int size);

    ApiResult<CustomPage<UserResDto>> search(String some, int page, int size);

    ApiResult<CustomPage<UserResDto>> getFilterRole(RoleTypeEnum roleTypeEnum, int page, int size);

    ApiResult<UserResDto> add(UserReqDto userReqDto);

    ApiResult<UserResDto> blockOrUnblock( UUID id);

    ApiResult<?> delete(UUID id);

}
