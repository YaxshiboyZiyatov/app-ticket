package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.EventReqDto;
import ai.ecma.appticketserver.payload.EventResDto;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author Muhammad Mo'minov
 * 17.09.2021
 */
@RequestMapping(AppConstant.EVENT_CONTROLLER)
@Tag(name = "Event controller", description = "Bu event controller")
public interface EventController {

    @GetMapping("/{id}")
    ApiResult<EventResDto> getById(@PathVariable UUID id);

    @GetMapping
    ApiResult<CustomPage<EventResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @PostMapping
    ApiResult<EventResDto> addEvent(@RequestBody EventReqDto eventReqDto);

    @PutMapping("/{id}")
    ApiResult<EventResDto> editEvent(@RequestBody EventReqDto eventReqDto, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    ApiResult<?> deleteEvent(@PathVariable UUID id);

}
