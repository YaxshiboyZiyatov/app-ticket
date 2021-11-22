package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Ticket;
import ai.ecma.appticketserver.enums.SeatStatusEnum;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.payload.*;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface TicketService {

    ApiResult<CustomPage<TicketResDto>> getByStatus(SeatStatusEnum seatStatusEnum, int page, int size);

    ApiResult<TicketResDto> getById(UUID id);

    ApiResult<CustomPage<TicketResDto>> getAll(int page, int size);

    ApiResult<TicketResDto> AddTicket(TicketReqDto ticketReqDto);

    ApiResult<TicketResDto> editTicket(TicketReqDto ticketReqDto, UUID id);

    ApiResult<TicketResDto> BuyTicket(TicketBuyReqDto ticketBuyReqDto);

    ApiResult<CustomPage<TicketResDto>> getAllSoldTickets(UUID eventSessionId, int page, int size);

    ApiResult<TicketResDto> BronTicket(TicketBuyReqDto ticketBuyReqDto);

}
