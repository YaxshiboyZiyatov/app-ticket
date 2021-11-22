package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.RoleReqDto;
import ai.ecma.appticketserver.payload.RoleResDto;

import java.util.UUID;

public interface RoleService {

    ApiResult<CustomPage<RoleResDto>> getAll(int page, int size);

    ApiResult<RoleResDto> get(UUID id);

    ApiResult<RoleResDto> add(RoleReqDto roleReqDto);

    ApiResult<RoleResDto> edit(RoleReqDto roleReqDto, UUID id);

    ApiResult<?> delete(UUID id);

}
