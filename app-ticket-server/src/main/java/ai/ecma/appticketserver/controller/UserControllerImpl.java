package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.RoleTypeEnum;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Muhammad Mo'minov
 * 11.10.2021
 */
@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService;

    @Override
    public ApiResult<?> changePassword(User currentUser, ChangePasswordDto changePasswordDto) {
        return userService.changePassword(currentUser, changePasswordDto);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_CLIENT')")
    @Override
    public ApiResult<UserResDto> get(UUID id) {
        return userService.get(id);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_CLIENT')")
    @Override
    public ApiResult<CustomPage<UserResDto>> getAll(int page, int size) {
        return userService.getAll(page, size);
    }

    @Override
    public ApiResult<UserResDto> add(UserReqDto userReqDto) {
        return userService.add(userReqDto);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_CLIENT')")
    @Override
    public ApiResult<UserResDto> blockOrUnblock(UUID id) {
        return userService.blockOrUnblock(id);
    }

    @PreAuthorize("hasAnyAuthority('EDIT_CLIENT')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return userService.delete(id);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_CLIENT')")
    @Override
    public ApiResult<CustomPage<UserResDto>> search(String some, int page, int size) {
        return userService.search(some, page, size);
    }

    @PreAuthorize("hasAnyAuthority('VIEW_CLIENT')")
    @Override
    public ApiResult<CustomPage<UserResDto>> getFilterRole(RoleTypeEnum roleTypeEnum, int page, int size) {
        return userService.getFilterRole(roleTypeEnum, page, size);
    }
}
