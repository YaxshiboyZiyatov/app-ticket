package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(AppConstant.SEAT_TEMPLATE_CHAIR)
@Tag(name = "SeatTemplateChair controller", description = "Bu SeatTemplateChair controller")
public interface SeatTemplateChairController {

    @GetMapping("/{id}")
    ApiResult<SeatTemplateChairResDto> getById(@PathVariable UUID id);

    @GetMapping
    ApiResult<CustomPage<SeatTemplateChairResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @PostMapping
    ApiResult<SeatTemplateChairResDto> create(@RequestBody @Valid SeatTemplateChairReqDto seatTemplateChairReqDto);

//    @PostMapping
//    ApiResult<SeatTemplateChairResDto> create(@RequestBody @Valid SeatTemplateChairReqDto seatTemplateChairReqDto);

    @PutMapping("/{id}")
    ApiResult<SeatTemplateChairResDto> edit(@RequestBody SeatTemplateChairReqDto seatTemplateChairReqDto, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

}
