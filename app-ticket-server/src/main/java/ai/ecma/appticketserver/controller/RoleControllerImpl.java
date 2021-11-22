package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.RoleReqDto;
import ai.ecma.appticketserver.payload.RoleResDto;
import ai.ecma.appticketserver.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RoleService roleService;
    @PreAuthorize("hasAuthority('VIEW_ROLE')")
    @Override
    public ApiResult<CustomPage<RoleResDto>> getAll(int page, int size) {
        return roleService.getAll(page, size);
    }
    @PreAuthorize("hasAuthority('VIEW_ROLE')")
    @Override
    public ApiResult<RoleResDto> get(UUID id) {
        return roleService.get(id);
    }
    @PreAuthorize("hasAuthority('ADD_ROLE')")
    @Override
    public ApiResult<RoleResDto> add(RoleReqDto roleReqDto) {
        return roleService.add(roleReqDto);
    }
    @PreAuthorize("hasAuthority('EDIT_ROLE')")
    @Override
    public ApiResult<RoleResDto> edit(RoleReqDto roleReqDto, UUID id) {
        return roleService.edit(roleReqDto, id);
    }
    @PreAuthorize("hasAuthority('DELETE_ROLE')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return roleService.delete(id);
    }
}
