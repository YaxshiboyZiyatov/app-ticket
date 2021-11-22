package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import static ai.ecma.appticketserver.utils.AppConstant.EVENT_SESSION_CONTROLLER;

@RequestMapping(EVENT_SESSION_CONTROLLER)
@Tag(name = "EventSession controller", description = "Bu EventSession controller")
public interface EventSessionController {

    @GetMapping("get-by-times")
    ApiResult<CustomPage<EventSessionResDto>> getByPlaceIdAndTimes(@RequestBody @Valid GetByTimesEventSessionDto getByTimesEventSessionDto, @RequestParam int page, @RequestParam int size );

    @GetMapping("get-by-event-id/{id}")
    ApiResult<CustomPage<EventSessionResDto>> getByEventID(@PathVariable UUID id, @RequestParam int page, @RequestParam int size );

    @GetMapping
    ApiResult<CustomPage<EventSessionResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @GetMapping("/{id}")
    ApiResult<EventSessionResDto> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<EventSessionResDto> creat(@RequestBody @Valid EventSessionReqDto eventSessionReqDto);

    @PutMapping("/{id}")
    ApiResult<EventSessionResDto> edit(@PathVariable UUID id, @RequestBody EventSessionReqDto eventSessionReqDto);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

    @DeleteMapping
    ApiResult<?> deleteAll();
}
