package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.enums.SeatStatusEnum;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.service.TicketService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TicketControllerImpl implements TicketController{
    private final TicketService ticketService;

    public TicketControllerImpl(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public ApiResult<CustomPage<TicketResDto>> getStatus(int page, int size, SeatStatusEnum seatStatusEnum) {
        return ticketService.getByStatus(seatStatusEnum, page, size);
    }

    @PreAuthorize("hasAuthority('VIEW_TICKET')")
    @Override
    public ApiResult<TicketResDto> getOne(UUID id) {
        return ticketService.getById(id);
    }
//    @PreAuthorize("hasAuthority('VIEW_TICKET')")
    @Override
    public ApiResult<CustomPage<TicketResDto>> getAll(int page, int size) {
        return ticketService.getAll(page,size);
    }

    @PreAuthorize("hasAuthority('ADD_TICKET')")
    @Override
    public ApiResult<TicketResDto> addTicket(TicketReqDto ticketReqDto) {
        return ticketService.AddTicket(ticketReqDto);
    }
    @PreAuthorize("hasAuthority('EDIT_TICKET')")
    @Override
    public ApiResult<TicketResDto> editTicket(UUID id, TicketReqDto ticketReqDto) {
        return ticketService.editTicket(ticketReqDto,id);
    }
    @PreAuthorize("hasAuthority('EDIT_TICKET')")
    @Override
    public ApiResult<TicketResDto> buyTicket(TicketBuyReqDto ticketBuyReqDto) {
        return ticketService.BuyTicket(ticketBuyReqDto);
    }

}
