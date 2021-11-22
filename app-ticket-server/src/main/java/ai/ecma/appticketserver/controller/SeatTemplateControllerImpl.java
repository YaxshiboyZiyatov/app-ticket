package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.SeatTemplateReqDto;
import ai.ecma.appticketserver.payload.SeatTemplateResDto;
import ai.ecma.appticketserver.service.SeatTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SeatTemplateControllerImpl implements SeatTemplateController {
    private final SeatTemplateService seatTemplateService;

    //    @PreAuthorize("hasAuthority('VIEW_SEAT_TEMPLATE')")
    @Override
    public ApiResult<SeatTemplateResDto> getById(UUID id) {
        return seatTemplateService.getById(id);
    }

    //    @PreAuthorize("hasAuthority('VIEW_SEAT_TEMPLATE')")
    @Override
    public ApiResult<CustomPage<SeatTemplateResDto>> getAll(int page, int size) {
        return seatTemplateService.getAll(page, size);
    }

    //    @PreAuthorize("hasAuthority('ADD_SEAT_TEMPLATE')")
    @Override
    public ApiResult<SeatTemplateResDto> create(SeatTemplateReqDto seatTemplateReqDto) {
        return seatTemplateService.create(seatTemplateReqDto);
    }

    //    @PreAuthorize("hasAuthority('EDIT_SEAT_TEMPLATE')")
    @Override
    public ApiResult<SeatTemplateResDto> edit(SeatTemplateReqDto seatTemplateReqDto, UUID id) {
        return seatTemplateService.edit(seatTemplateReqDto, id);
    }

    //    @PreAuthorize("hasAuthority('DELETE_SEAT_TEMPLATE')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return seatTemplateService.delete(id);
    }
}
