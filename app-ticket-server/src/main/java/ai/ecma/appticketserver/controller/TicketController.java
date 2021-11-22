package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.enums.BronStatusEnum;
import ai.ecma.appticketserver.enums.SeatStatusEnum;
import ai.ecma.appticketserver.aop.CurrentUser;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping(AppConstant.TICKET_CONTROLLER)
@Tag(name = "Ticket controller", description = "Bu Ticket controller")
public interface TicketController {

    @GetMapping("/filter-by-status")
    ApiResult<CustomPage<TicketResDto>> getStatus(@RequestParam int page, @RequestParam int size, @RequestBody SeatStatusEnum seatStatusEnum);

    @GetMapping("/getById/{id}")
    ApiResult<TicketResDto> getOne(@PathVariable UUID id);

    @GetMapping("/getAll")
    ApiResult<CustomPage<TicketResDto>> getAll(@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE) int size);

    @PostMapping("/addTicket")
    ApiResult<TicketResDto> addTicket(@RequestBody TicketReqDto ticketReqDto);

    @PostMapping("/editTicket/{id}")
    ApiResult<TicketResDto> editTicket(@PathVariable UUID id,@RequestBody TicketReqDto ticketReqDto);

    @PostMapping("/buyTicket")
    ApiResult<TicketResDto>buyTicket(@RequestBody TicketBuyReqDto ticketBuyReqDto);
}
