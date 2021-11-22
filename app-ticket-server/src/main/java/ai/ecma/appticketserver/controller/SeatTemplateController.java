package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(AppConstant.SEAT_TEMPLATE)
@Tag(name = "SeatTemplate controller", description = "Bu SeatTemplate controller")
public interface SeatTemplateController {

    @GetMapping("/{id}")
    ApiResult<SeatTemplateResDto> getById(@PathVariable UUID id);

    @GetMapping
    ApiResult<CustomPage<SeatTemplateResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @PostMapping
    ApiResult<SeatTemplateResDto> create(@RequestBody SeatTemplateReqDto seatTemplateReqDto);

    @PutMapping("/{id}")
    ApiResult<SeatTemplateResDto> edit(@RequestBody SeatTemplateReqDto seatTemplateReqDto, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

}
