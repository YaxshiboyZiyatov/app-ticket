package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.SeatTemplateChairReqDto;
import ai.ecma.appticketserver.payload.SeatTemplateChairResDto;
import ai.ecma.appticketserver.service.SeatTemplateChairService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class SeatTemplateChairControllerImpl implements SeatTemplateChairController {
    private final SeatTemplateChairService seatTemplateChairService;
    @PreAuthorize("hasAuthority('VIEW_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<SeatTemplateChairResDto> getById(UUID id) {
        return seatTemplateChairService.getById(id);
    }
    @PreAuthorize("hasAuthority('VIEW_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<CustomPage<SeatTemplateChairResDto>> getAll(int page, int size) {
        return seatTemplateChairService.getAll(page, size);
    }
    @PreAuthorize("hasAuthority('ADD_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<SeatTemplateChairResDto> create(SeatTemplateChairReqDto seatTemplateReqDto) {
        return seatTemplateChairService.create(seatTemplateReqDto);
    }
    @PreAuthorize("hasAuthority('EDIT_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<SeatTemplateChairResDto> edit(SeatTemplateChairReqDto seatTemplateReqDto, UUID id) {
        return seatTemplateChairService.edit(seatTemplateReqDto, id);
    }
    @PreAuthorize("hasAuthority('DELETE_SEAT_TEMPLATE_CHAIR')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return seatTemplateChairService.delete(id);
    }
}

