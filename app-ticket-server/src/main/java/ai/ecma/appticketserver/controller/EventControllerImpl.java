package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.EventReqDto;
import ai.ecma.appticketserver.payload.EventResDto;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.awt.print.Book;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventControllerImpl implements EventController {

    private final EventService eventService;

    @Override
    public ApiResult<EventResDto> getById(UUID id) {
        return eventService.getById(id);
    }

    @Operation(summary = "Get all events")
    @Override
    public ApiResult<CustomPage<EventResDto>> getAll(int page, int size) {
        return eventService.getAll(page, size);
    }

    @PreAuthorize("hasAuthority('ADD_EVENT')")
    @Override
    public ApiResult<EventResDto> addEvent(EventReqDto eventReqDto) {
        return eventService.addEvent(eventReqDto);
    }

    @PreAuthorize("hasAuthority('EDIT_EVENT')")
    @Override
    public ApiResult<EventResDto> editEvent(EventReqDto eventReqDto, UUID id) {
        return eventService.editEvent(eventReqDto, id);
    }

    @PreAuthorize("hasAuthority('DELETE_EVENT')")
    @Override
    public ApiResult<?> deleteEvent(UUID id) {
        return eventService.deleteEvent(id);
    }
}
