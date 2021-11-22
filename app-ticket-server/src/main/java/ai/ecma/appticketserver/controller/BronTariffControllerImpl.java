package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.BronTariffReqDto;
import ai.ecma.appticketserver.payload.BronTariffResDto;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.service.BronTariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BronTariffControllerImpl implements BronTariffController{


   final BronTariffService bronTariffService;

    @PreAuthorize("hasAuthority('ADD_BRON_TARIFF')")
    @Override
    public ApiResult<CustomPage<BronTariffResDto>> getAll(int page, int size) {
        return bronTariffService.getAll(page,size);
    }
    @PreAuthorize("hasAuthority('ADD_BRON_TARIFF')")
    @Override
    public ApiResult<BronTariffResDto> get(UUID id) {
        return bronTariffService.get(id);
    }

    @PreAuthorize("hasAuthority('ADD_BRON_TARIFF')")
    @Override
    public ApiResult<BronTariffResDto> create(BronTariffReqDto bronTariffReqDto) {
        return bronTariffService.create(bronTariffReqDto);
    }

    @PreAuthorize("hasAuthority('EDIT_BRON_TARIFF')")
    @Override
    public ApiResult<BronTariffResDto> edit(BronTariffReqDto bronTariffReqDto, UUID id) {
        return bronTariffService.edit(bronTariffReqDto,id);
    }
    @PreAuthorize("hasAuthority('DELETE_BRON_TARIFF')")
    @Override
    public ApiResult<?> deleteAll() {
        return bronTariffService.deleteAll();
    }
    @PreAuthorize("hasAuthority('DELETE_BRON_TARIFF')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return bronTariffService.delete(id);
    }
}
