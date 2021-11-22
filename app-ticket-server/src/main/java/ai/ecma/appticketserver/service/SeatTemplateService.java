package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.SeatTemplate;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.SeatTemplateReqDto;
import ai.ecma.appticketserver.payload.SeatTemplateResDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

public interface SeatTemplateService {

    ApiResult<SeatTemplateResDto> getById(UUID id);

    ApiResult<CustomPage<SeatTemplateResDto>> getAll(int page, int size);

    ApiResult<SeatTemplateResDto> create(SeatTemplateReqDto seatTemplateReqDto);

    ApiResult<SeatTemplateResDto> edit(SeatTemplateReqDto seatTemplateReqDto, UUID id);

    ApiResult<?> delete(UUID id);

    SeatTemplateResDto seatTemplateResDtoTo(SeatTemplate seatTemplate);

}
