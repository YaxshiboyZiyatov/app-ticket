package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.RoleReqDto;
import ai.ecma.appticketserver.payload.RoleResDto;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(AppConstant.ROLE_CONTROLLER)
@Tag(name = "Role controller", description = "Bu Role controller")

public interface RoleController {

    @GetMapping
    ApiResult<CustomPage<RoleResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @GetMapping("/{id}")
    ApiResult<RoleResDto> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<RoleResDto> add(@RequestBody RoleReqDto roleReqDto);

    @PutMapping("/{id}")
    ApiResult<RoleResDto> edit(@RequestBody RoleReqDto roleReqDto, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

}
