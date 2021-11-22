package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.EventReqDto;
import ai.ecma.appticketserver.payload.EventResDto;
import ai.ecma.appticketserver.payload.CustomPage;

import java.util.UUID;

public interface EventService {

    ApiResult<EventResDto> getById(UUID id);

    ApiResult<CustomPage<EventResDto>> getAll(int page, int size);

    ApiResult<EventResDto> addEvent(EventReqDto eventReqDto);

    ApiResult<EventResDto> editEvent(EventReqDto eventReqDto, UUID id);

    ApiResult<?> deleteEvent(UUID id);

}
