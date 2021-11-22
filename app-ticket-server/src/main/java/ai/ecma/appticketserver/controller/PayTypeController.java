package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.PayTypeReqDto;
import ai.ecma.appticketserver.payload.PayTypeResDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static ai.ecma.appticketserver.utils.AppConstant.PAY_TYPE_CONTROLLER;

@RequestMapping(PAY_TYPE_CONTROLLER)
@Tag(name = "PayType controller", description = "Bu PayType controller")
public interface PayTypeController {

    @GetMapping
    ApiResult<List<PayTypeResDto>> getAll();

    @GetMapping("/{id}")
    ApiResult<PayTypeResDto> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<PayTypeResDto> create(@RequestBody PayTypeReqDto payTypeDto);

    @PutMapping("/{id}")
    ApiResult<PayTypeResDto> edit(@RequestBody PayTypeReqDto payTypeDto, @PathVariable UUID id);

    @DeleteMapping
    ApiResult<?> deleteAll();

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);




}
