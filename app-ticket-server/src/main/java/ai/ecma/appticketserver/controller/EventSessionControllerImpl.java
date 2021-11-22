package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.service.EventSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
public class EventSessionControllerImpl implements EventSessionController {
    private final EventSessionService eventSessionService;

    @Override
    public ApiResult<CustomPage<EventSessionResDto>> getByPlaceIdAndTimes(GetByTimesEventSessionDto getByTimesEventSessionDto, int page, int size) {
        return eventSessionService.getByPlaceIdAndTimes(getByTimesEventSessionDto, page, size);
    }

    @Override
    public ApiResult<CustomPage<EventSessionResDto>> getByEventID(UUID id, int page, int size) {
        return eventSessionService.getByEventID(id, page, size);
    }

    @PreAuthorize("hasAuthority('EDIT_EVENT')")
    @Override
    public ApiResult<CustomPage<EventSessionResDto>> getAll(int page, int size) {
        return eventSessionService.getAll(page, size);
    }
    @PreAuthorize("hasAuthority('EDIT_EVENT')")
    @Override
    public ApiResult<EventSessionResDto> get(UUID id) {
        return eventSessionService.get(id);
    }
    @PreAuthorize("hasAuthority('EDIT_EVENT')")
    @Override
    public ApiResult<EventSessionResDto> creat(EventSessionReqDto eventSessionReqDto) {
        return eventSessionService.creat(eventSessionReqDto);
    }
    @PreAuthorize("hasAuthority('EDIT_EVENT')")
    @Override
    public ApiResult<EventSessionResDto> edit(UUID id, EventSessionReqDto eventSessionReqDto) {
        return eventSessionService.edit(id, eventSessionReqDto);
    }
    @PreAuthorize("hasAuthority('DELETE_EVENT')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return eventSessionService.delete(id);
    }

    @PreAuthorize("hasAuthority('DELETE_EVENT')")
    @Override
    public ApiResult<?> deleteAll() {
        return eventSessionService.deleteAll();
    }
}
