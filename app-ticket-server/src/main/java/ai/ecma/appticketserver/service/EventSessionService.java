package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.EventSession;
import ai.ecma.appticketserver.payload.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface EventSessionService {

    ApiResult<CustomPage<EventSessionResDto>> getByPlaceIdAndTimes(GetByTimesEventSessionDto getByTimesEventSessionDto, int page, int size);


    ApiResult<CustomPage<EventSessionResDto>> getByEventID(UUID id, int page, int size);

    ApiResult<CustomPage<EventSessionResDto>> getAll(int page, int size);

    ApiResult<EventSessionResDto> get(UUID id);

    ApiResult<EventSessionResDto>creat(EventSessionReqDto eventSessionReqDto);

    ApiResult<EventSessionResDto> edit(UUID id, EventSessionReqDto eventSessionReqDto);

    ApiResult<?> delete(UUID id);

    ApiResult<?> deleteAll();
}
