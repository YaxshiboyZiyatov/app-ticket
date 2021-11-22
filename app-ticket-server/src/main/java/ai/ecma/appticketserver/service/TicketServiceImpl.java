package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.EventSession;
import ai.ecma.appticketserver.entity.Ticket;
import ai.ecma.appticketserver.entity.TicketHistory;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.SeatStatusEnum;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.EventSessionRepository;
import ai.ecma.appticketserver.repository.TicketHistoryRepository;
import ai.ecma.appticketserver.repository.TicketRepository;
import ai.ecma.appticketserver.repository.UserRepository;
import ai.ecma.appticketserver.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    private final EventSessionRepository eventSessionRepository;

    private final TicketHistoryRepository ticketHistoryRepository;


    @Override
    public ApiResult<CustomPage<TicketResDto>> getByStatus(SeatStatusEnum seatStatusEnum, int page, int size) {
        Pageable pageable= PageRequest.of(page, size);
        Page<Ticket> allByStatusEnum = ticketRepository.findAllByStatusEnum(seatStatusEnum, pageable);
        CustomPage<TicketResDto> ticketResDtoCustomPage = mapTicketResDtoCustomPage(allByStatusEnum);
        return ApiResult.successResponse(ticketResDtoCustomPage);
    }

    @Override
    public ApiResult<TicketResDto> getById(UUID id) {

        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new RestException("Ticket not found!",
                HttpStatus.NOT_FOUND));

        return ApiResult.successResponse(Mapper.ticketResDto(ticket));
    }

    @Override
    public ApiResult<CustomPage<TicketResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "statusEnum"));
        Page<Ticket> all = ticketRepository.findAll(pageable);
        CustomPage<TicketResDto> ticketResDtoCustomPage = mapTicketResDtoCustomPage(all);
        return ApiResult.successResponse("All ticket List", ticketResDtoCustomPage);

    }

    @Override
    public ApiResult<TicketResDto> AddTicket(TicketReqDto ticketReqDto) {
        try {
            Ticket ticket = new Ticket();
            ticket.setSeatName(ticketReqDto.getSeatName());
//        ticket.setUser(userRepository.findById(ticketReqDto.getUserId()).orElseThrow(()
//                -> new RestException("User not found!", HttpStatus.NOT_FOUND))); // buni qayta ko'rib chiqish kk

            ticket.setEventSession(eventSessionRepository.findById(ticketReqDto.getEventSessionId()).orElseThrow(()
                    -> new RestException("EventSession not found!", HttpStatus.NOT_FOUND)));

            ticket.setStatusEnum(ticketReqDto.getSeatStatusEnum()); // buni qayta ko'rib chiqish kk,
            ticket.setRow(ticketReqDto.getRow());
            ticket.setSection(ticketReqDto.getSection());
            ticket.setPrice(ticketReqDto.getPrice());

            ticketRepository.save(ticket);
            return ApiResult.successResponse("TICKET SAVED SUCCESSFULLY", Mapper.ticketResDto(ticket));
        }catch (DataIntegrityViolationException e){
            throw new RestException("Bu ticket allaqachon mavjud",HttpStatus.CONFLICT);
        }
    }

    @Override
    public ApiResult<TicketResDto> editTicket(TicketReqDto ticketReqDto, UUID id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(()
                -> new RestException("Ticket not found", HttpStatus.NOT_FOUND));
        ticket.setSeatName(ticket.getSeatName());
        ticket.setEventSession(eventSessionRepository.findById(ticketReqDto.getEventSessionId()).orElseThrow(()
                -> new RestException("EventSession not found!", HttpStatus.NOT_FOUND)));
        ticket.setRow(ticket.getRow());
        ticket.setStatusEnum(ticketReqDto.getSeatStatusEnum());
        ticket.setSection(ticketReqDto.getSection());
        ticket.setPrice(ticketReqDto.getPrice());
        ticketRepository.save(ticket);

        return ApiResult.successResponse(Mapper.ticketResDto(ticket));
    }

    @Override // qayta ko'rib chiqish kk manimcha
    public ApiResult<TicketResDto> BuyTicket(TicketBuyReqDto ticketBuyReqDto) {
        Ticket ticket = ticketRepository.findById(ticketBuyReqDto.getTicketId()).orElseThrow(()
                -> new RestException("Ticket not found", HttpStatus.NOT_FOUND));

        User user = userRepository.findById(ticketBuyReqDto.getUserId()).orElseThrow(()
                -> new RestException("User not found", HttpStatus.NOT_FOUND));

        EventSession eventSession = eventSessionRepository.findById(ticketBuyReqDto.getEventSessionId()).orElseThrow(()
                -> new RestException("Event not found", HttpStatus.NOT_FOUND));


        if (!ticket.isTicketUsed() && (ticket.getStatusEnum() != SeatStatusEnum.BOOKED || ticket.getStatusEnum() != SeatStatusEnum.SOLD ||
                ticket.getStatusEnum() != SeatStatusEnum.RESERVED ||
                ticket.getStatusEnum() != SeatStatusEnum.VIP)) {

            Date data = new Date();
            ticket.setUser(user);
            ticket.setSoldTime(new Timestamp(data.getTime()));
            ticket.setTicketUsed(true);
            ticketRepository.save(ticket);

            // qayta ko'rib chiqish kk
            TicketHistory ticketHistory = new TicketHistory();
            ticketHistory.setTicket(ticket);
            ticketHistory.setUser(user);
            ticketHistory.setSeatStatusEnum(ticket.getStatusEnum());
            ticketHistoryRepository.save(ticketHistory);


            return ApiResult.successResponse(Mapper.ticketResDto(ticket));
        }

        return ApiResult.failResponse("This ticket has already booked bratan");


    }

    @Override
    public ApiResult<CustomPage<TicketResDto>> getAllSoldTickets(UUID eventSessionId, int page, int size) {

        PageRequest pagep = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "created_at"));

        Page<Ticket> returnPage = ticketRepository.findAllByEventSessionIdAndStatusEnum(eventSessionId, SeatStatusEnum.SOLD, pagep);

        CustomPage<TicketResDto> mapTicketResDtoCustomPage = mapTicketResDtoCustomPage(returnPage);

        return ApiResult.successResponse(mapTicketResDtoCustomPage);
    }

    @Override
    public ApiResult<TicketResDto> BronTicket(TicketBuyReqDto ticketBuyReqDto) {

        return null;
    }



    private CustomPage<TicketResDto> mapTicketResDtoCustomPage(Page<Ticket> ticketPage) {
        return new CustomPage<>(
                ticketPage.getContent()
                        .stream()
                        .map(Mapper::ticketResDto)
                        .collect(Collectors.toList()),
                ticketPage.getNumberOfElements(),
                ticketPage.getNumber(),
                ticketPage.getTotalElements(),
                ticketPage.getTotalPages(),
                ticketPage.getSize()
        );
    }


}
