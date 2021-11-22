package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.PlaceReqDto;
import ai.ecma.appticketserver.payload.PlaceResDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static ai.ecma.appticketserver.utils.AppConstant.PLACE_CONTROLLER;

@RequestMapping(PLACE_CONTROLLER)
@Tag(name = "Place controller", description = "Bu Place controller")
public interface PlaceController {
    /**
     * Abdulhamid aka joy, 2 ta vaqt bor shu vaqt oralig'ida qanday konsertlar bor page
     **/

    @GetMapping
    ApiResult<CustomPage<PlaceResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @GetMapping("/{id}")
    ApiResult<PlaceResDto> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<PlaceResDto> creat(@RequestBody @Valid PlaceReqDto placeReqDto);

    @PutMapping("/{id}")
    ApiResult<PlaceResDto> edit(@PathVariable UUID id, @RequestBody PlaceReqDto placeReqDto);

    @DeleteMapping("{id}")
    ApiResult<?> delete(@PathVariable UUID id);

    @DeleteMapping
    ApiResult<?> deleteAll();

}
