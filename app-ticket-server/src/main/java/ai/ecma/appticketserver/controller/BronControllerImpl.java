package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.BronStatusEnum;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.service.BronService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BronControllerImpl implements BronController {

    private final BronService bronService;

    @PreAuthorize("hasAuthority('VIEW_BRON')")
    @Override
    public ApiResult<CustomPage<BronResDto>> getStatus(int page, int size, BronStatusEnum bronStatus) {
        return bronService.getStatus(page, size, bronStatus);
    }

    @PreAuthorize("hasAuthority('VIEW_BRON')")
    @Override
    public ApiResult<CustomPage<BronResDto>> getByUserId(int page, int size, User user) {
        return bronService.getByUserId(page, size, user);
    }

    @PreAuthorize("hasAuthority('VIEW_BRON')")
    @Override
    public ApiResult<CustomPage<BronResDto>> getAll(int page, int size) {
        return bronService.getAll(page, size);
    }

    @PreAuthorize("hasAuthority('VIEW_BRON')")
    @Override
    public ApiResult<BronResDto> getById(UUID id) {
        return bronService.getById(id);
    }

    //    @PreAuthorize("hasAuthority('ADD_BRON')")
    @Override
    public ApiResult<BronResDto> add(User user, BronReqDto bronReqDto) {
        return bronService.add(user, bronReqDto);
    }

    //    @PreAuthorize("hasAuthority('CANCEL_BRON')")
    @Override
    public ApiResult<?> cancelBron(User user, BronCancelDto bronCancelDto) {
        return bronService.cancelBron(user, bronCancelDto);
    }

    @Override
    public ApiResult<?> bookedTicketBuy(User user, TicketBuyDto ticketBuyDto) {
        return bronService.bookedTicketBuy(user, ticketBuyDto);
    }


    @Override
    public ApiResult<BronResDto> edit(UUID id, BronReqDto bronReqDto) {
        return bronService.edit(id, bronReqDto);
    }

    @PreAuthorize("hasAuthority('DELETE_BRON')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return bronService.delete(id);
    }

    @Override
    public ApiResult<?> ticketBuy(User user, OrderPayDto orderPayDto) {
        return bronService.ticketBuy(user, orderPayDto);
    }

}
