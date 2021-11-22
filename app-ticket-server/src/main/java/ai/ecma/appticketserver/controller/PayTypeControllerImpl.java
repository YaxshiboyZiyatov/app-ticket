package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.PayTypeReqDto;
import ai.ecma.appticketserver.payload.PayTypeResDto;
import ai.ecma.appticketserver.service.PayTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class PayTypeControllerImpl implements PayTypeController {
    @Autowired
    PayTypeService payTypeService;

    @PreAuthorize("hasAuthority('ADD_PAY_TYPE')")
    @Override
    public ApiResult<List<PayTypeResDto>> getAll() {
        return payTypeService.getAll();
    }
    @PreAuthorize("hasAuthority('ADD_PAY_TYPE')")
    @Override
    public ApiResult<PayTypeResDto> get(UUID id) {
        return payTypeService.get(id);
    }
    @PreAuthorize("hasAuthority('ADD_PAY_TYPE')")
    @Override
    public ApiResult<PayTypeResDto> create(PayTypeReqDto payTypeDto) {
        return payTypeService.create(payTypeDto);
    }
    @PreAuthorize("hasAuthority('EDIT_PAY_TYPE')")
    @Override
    public ApiResult<PayTypeResDto> edit(PayTypeReqDto payTypeDto, UUID id) {
        return payTypeService.edit(payTypeDto, id);
    }
    @PreAuthorize("hasAuthority('DELETE_PAY_TYPE')")
    @Override
    public ApiResult<?> deleteAll() {
        return payTypeService.deleteAll();
    }

    @PreAuthorize("hasAuthority('DELETE_PAY_TYPE')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return payTypeService.delete(id);
    }
}
