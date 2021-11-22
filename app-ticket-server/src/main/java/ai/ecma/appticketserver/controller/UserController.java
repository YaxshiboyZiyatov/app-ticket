package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.aop.CurrentUser;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.RoleTypeEnum;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * @author Muhammad Mo'minov
 * 11.10.2021
 */
@RequestMapping(AppConstant.USER_CONTROLLER)
@Tag(name = "User controller", description = "Bu User controller")
public interface UserController {

    @PutMapping("/change-password")
    ApiResult<?> changePassword(@CurrentUser User currentUser, @RequestBody @Valid ChangePasswordDto changePasswordDto);

    @GetMapping("/{id}")
    ApiResult<UserResDto> get(@PathVariable UUID id);

    @GetMapping
    ApiResult<CustomPage<UserResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @PostMapping
    ApiResult<UserResDto> add(@RequestBody UserReqDto userReqDto);

    @PutMapping("/block-unblock/{id}")
    ApiResult<UserResDto> blockOrUnblock( @PathVariable UUID id);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

    @GetMapping("/search")
    ApiResult<CustomPage<UserResDto>> search(@RequestParam String some, @RequestParam int page, @RequestParam int size);

    @GetMapping("/filter-byRole")
    ApiResult<CustomPage<UserResDto>> getFilterRole(@RequestParam RoleTypeEnum roleTypeEnum, @RequestParam int page, @RequestParam int size);

}
