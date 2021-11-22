package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.TicketHistory;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.TicketHistoryResDto;
import ai.ecma.appticketserver.repository.TicketHistoryRepository;
import ai.ecma.appticketserver.repository.TicketRepository;
import ai.ecma.appticketserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketHistoryServiceImpl implements TicketHistoryService{

    private final TicketHistoryRepository ticketHistoryRepository;
    private final UserRepository userRepository;

    private final TicketRepository ticketRepository;



    @Override
    public ApiResult<CustomPage<TicketHistoryResDto>> getTicketHistory(UUID userId,int page, int size) {


//        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()
//                -> new RestException("Ticket not found", HttpStatus.NOT_FOUND));

//        User user = userRepository.findById(userId).orElseThrow(()
//                -> new RestException("User not found", HttpStatus.NOT_FOUND));
//
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Ticket> all = (Page<Ticket>) ticketRepository.findAllByUserId(userId); //
//
//        CustomPage<TicketHistoryResDto> ticketHistoryResDtoCustomPage = mapTicketResDtoCustomPage(all);
//        return ApiResult.successResponse("All ticket List",ticketHistoryResDtoCustomPage);

        return null; // to'liq yakunlanmadi
    }

    // for admins
    @Override
    public ApiResult<CustomPage<TicketHistoryResDto>> getAllTicketHis(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TicketHistory> all = ticketHistoryRepository.findAll(pageable);
        CustomPage<TicketHistoryResDto> ticketResDtoCustomPage = mapTicketResDtoCustomPage(all);


        return ApiResult.successResponse("All ticket List",ticketResDtoCustomPage);
    }


    public TicketHistoryResDto ticketHistoryResDtoTo(TicketHistory ticketHistory){
        return new TicketHistoryResDto(
                ticketHistory.getId(),
                !Objects.isNull(ticketHistory.getTicket()) ? ticketHistory.getTicket().getId() :null,
                !Objects.isNull(ticketHistory.getUser()) ? ticketHistory.getUser().getId() :null,
                ticketHistory.getSeatStatusEnum().name()
        );
    }



    private CustomPage<TicketHistoryResDto> mapTicketResDtoCustomPage(Page<TicketHistory> ticketPage) {
        return new CustomPage<>(
                ticketPage.getContent()
                        .stream()
                        .map(this::ticketHistoryResDtoTo)
                        .collect(Collectors.toList()),
                ticketPage.getNumberOfElements(),
                ticketPage.getNumber(),
                ticketPage.getTotalElements(),
                ticketPage.getTotalPages(),
                ticketPage.getSize()
        );
    }
}
