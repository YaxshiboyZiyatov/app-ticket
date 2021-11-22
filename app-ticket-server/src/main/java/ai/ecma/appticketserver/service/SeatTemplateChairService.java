package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.SeatTemplateChairReqDto;
import ai.ecma.appticketserver.payload.SeatTemplateChairResDto;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface SeatTemplateChairService {

    ApiResult<SeatTemplateChairResDto> getById(UUID id);

    ApiResult<CustomPage<SeatTemplateChairResDto>> getAll(int page, int size);

    ApiResult<SeatTemplateChairResDto> create(SeatTemplateChairReqDto seatTemplateChairReqDto);

    ApiResult<SeatTemplateChairResDto> edit(SeatTemplateChairReqDto seatTemplateChairReqDto, UUID id);

    ApiResult<?> delete(UUID id);

}
