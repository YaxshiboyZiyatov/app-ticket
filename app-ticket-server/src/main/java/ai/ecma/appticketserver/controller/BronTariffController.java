package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static ai.ecma.appticketserver.utils.AppConstant.PAY_TYPE_CONTROLLER;

@RequestMapping(AppConstant.BRON_TARIFF_CONTROLLER)
@Tag(name = "Bron tariff controller", description = "Bu bron tariff controller")
public interface BronTariffController {

    @GetMapping
    ApiResult<CustomPage<BronTariffResDto>> getAll(@RequestParam int page,@RequestParam int size);

    @GetMapping("/{id}")
    ApiResult<BronTariffResDto> get(@PathVariable UUID id);

    @PostMapping
    ApiResult<BronTariffResDto> create(@RequestBody BronTariffReqDto bronTariffReqDto);

    @PutMapping("/{id}")
    ApiResult<BronTariffResDto> edit(@RequestBody BronTariffReqDto bronTariffReqDto, @PathVariable UUID id);

    @DeleteMapping
    ApiResult<?> deleteAll();

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}


