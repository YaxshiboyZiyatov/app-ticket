package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.PayType;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.PayTypeReqDto;
import ai.ecma.appticketserver.payload.PayTypeResDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface PayTypeService {

    ApiResult<List<PayTypeResDto>> getAll();

    ApiResult<PayTypeResDto> get(UUID id);

    ApiResult<PayTypeResDto> create(PayTypeReqDto payTypeDto);

    ApiResult<PayTypeResDto> edit(PayTypeReqDto payTypeDto, UUID id);

    ApiResult<?> deleteAll();

    ApiResult<?> delete(UUID id);
}
