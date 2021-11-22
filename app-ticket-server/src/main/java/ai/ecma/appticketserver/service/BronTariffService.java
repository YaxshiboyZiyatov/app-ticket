package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.BronTariffReqDto;
import ai.ecma.appticketserver.payload.BronTariffResDto;
import ai.ecma.appticketserver.payload.CustomPage;

import java.util.UUID;

public interface BronTariffService {

    ApiResult<CustomPage<BronTariffResDto>> getAll(int page,int size);

    ApiResult<BronTariffResDto> get(UUID id);

    ApiResult<BronTariffResDto> create(BronTariffReqDto bronTariffReqDto);

    ApiResult<BronTariffResDto> edit(BronTariffReqDto bronTariffReqDto, UUID id);

    ApiResult<?> deleteAll();

    ApiResult<?> delete(UUID id);
}
