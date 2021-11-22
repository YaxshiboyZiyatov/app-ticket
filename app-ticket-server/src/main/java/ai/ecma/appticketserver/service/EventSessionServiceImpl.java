package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Event;
import ai.ecma.appticketserver.entity.EventSession;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.BronTariffRepository;
import ai.ecma.appticketserver.repository.EventRepository;
import ai.ecma.appticketserver.repository.EventSessionRepository;
import ai.ecma.appticketserver.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.omg.CORBA.Object;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventSessionServiceImpl implements EventSessionService {
    private final EventSessionRepository eventSessionRepository;
    private final EventRepository eventRepository;
    private final BronTariffRepository bronTariffRepository;

    @Override
    public ApiResult<CustomPage<EventSessionResDto>> getByPlaceIdAndTimes(GetByTimesEventSessionDto getByTimesEventSessionDto, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EventSession> byIdAndTimes = eventSessionRepository.getByIdAndTimes(getByTimesEventSessionDto.getStartTime(), getByTimesEventSessionDto.getEndTime(), getByTimesEventSessionDto.getPlaceId(), pageable);
        CustomPage<EventSessionResDto> eventSessionResDtoCustomPage = mapEventSessionResDtoCustomPage(byIdAndTimes);
        return ApiResult.successResponse(eventSessionResDtoCustomPage);
    }


    @Override
    public ApiResult<CustomPage<EventSessionResDto>> getByEventID(UUID id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EventSession> byEventId = eventSessionRepository.findByEventId(id, pageable);
        CustomPage<EventSessionResDto> eventSessionResDtoCustomPage = mapEventSessionResDtoCustomPage(byEventId);
        return ApiResult.successResponse(eventSessionResDtoCustomPage);
    }

    @Override
    public ApiResult<CustomPage<EventSessionResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EventSession> sessionPageAll = eventSessionRepository.findAll(pageable);
        CustomPage<EventSessionResDto> eventSessionResDtoCustomPage = mapEventSessionResDtoCustomPage(sessionPageAll);
        return ApiResult.successResponse(eventSessionResDtoCustomPage);
    }

    @Override
    public ApiResult<EventSessionResDto> get(UUID id) {
        EventSession eventSession = eventSessionRepository.findById(id).orElseThrow(() -> new RestException("EventSession not found", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(Mapper.eventSessionResDtoTo(eventSession));
    }

    @Override
    public ApiResult<EventSessionResDto> creat(EventSessionReqDto eventSessionReqDto) {
        Event event = eventRepository.findById(eventSessionReqDto.getEventId()).orElseThrow(() ->
                new RestException("Event not found", HttpStatus.NOT_FOUND));
        List<EventSession> conflictEventSessions = eventSessionRepository.findConflictEventSessions(
                eventSessionReqDto.getStartTime(),
                eventSessionReqDto.getEndTime(),
                event.getPlace().getId()
        );
        if (!conflictEventSessions.isEmpty())
            throw new RestException("Bu vaqtda boshqa tadbir bor", HttpStatus.CONFLICT, conflictEventSessions);
        EventSession eventSession = new EventSession(
                event,
                eventSessionReqDto.getStartTime(),
                eventSessionReqDto.getEndTime(),
                !Objects.isNull(eventSessionReqDto.getBronTariffId())
                        ? bronTariffRepository.findById(eventSessionReqDto.getBronTariffId()).orElseThrow(() ->
                        new RestException("BronTariff not found", HttpStatus.NOT_FOUND))
                        : null
                );
        eventSessionRepository.save(eventSession);
        return ApiResult.successResponse(Mapper.eventSessionResDtoTo(eventSession));
    }

    @Override
    public ApiResult<EventSessionResDto> edit(UUID id, EventSessionReqDto eventSessionReqDto) {
        EventSession eventSession = eventSessionRepository.findById(id).orElseThrow(() -> new RestException("EvenSession Not found", HttpStatus.NOT_FOUND));
        Event event = eventRepository.findById(eventSessionReqDto.getEventId()).orElseThrow(() -> new RestException("Event not found", HttpStatus.NOT_FOUND));
        List<EventSession> conflictEventSessions = eventSessionRepository.findConflictEventSessionsByID(eventSessionReqDto.getStartTime(), eventSessionReqDto.getEndTime(), event.getPlace().getId(), eventSession.getId());
        if (!conflictEventSessions.isEmpty())
            throw new RestException("Bu vaqtda boshqa tadbir bor", HttpStatus.CONFLICT, conflictEventSessions);
        eventSession.setEvent(eventRepository.findById(id).orElseThrow(() -> new RestException("Event Not found", HttpStatus.NOT_FOUND)));
        eventSession.setStartTime(eventSessionReqDto.getStartTime());
        eventSession.setEndTime(eventSessionReqDto.getEndTime());
        eventSession.setBronTariff(bronTariffRepository.findById(id).orElseThrow(() -> new RestException("BronTariff not found", HttpStatus.NOT_FOUND)));
        eventSessionRepository.save(eventSession);
        return ApiResult.successResponse(Mapper.eventSessionResDtoTo(eventSession));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            eventSessionRepository.deleteById(id);
            return ApiResult.successResponse("Success deleted!");
        } catch (Exception e) {
            throw new RestException("Event not found!", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ApiResult<?> deleteAll() {
        eventSessionRepository.deleteAll();
        return ApiResult.successResponse("Success deleted");
    }

    private CustomPage<EventSessionResDto> mapEventSessionResDtoCustomPage(Page<EventSession> eventSessionPage) {
        return new CustomPage<>(
                eventSessionPage.getContent()
                        .stream()
                        .map(Mapper::eventSessionResDtoTo)
                        .collect(Collectors.toList()),
                eventSessionPage.getNumberOfElements(),
                eventSessionPage.getNumber(),
                eventSessionPage.getTotalElements(),
                eventSessionPage.getTotalPages(),
                eventSessionPage.getSize()
        );
    }
}
