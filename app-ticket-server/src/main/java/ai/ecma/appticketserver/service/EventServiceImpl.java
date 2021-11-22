package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Event;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.EventReqDto;
import ai.ecma.appticketserver.payload.EventResDto;
import ai.ecma.appticketserver.repository.AttachmentRepository;
import ai.ecma.appticketserver.repository.EventRepository;
import ai.ecma.appticketserver.repository.PlaceRepository;
import ai.ecma.appticketserver.utils.CommonUtils;
import ai.ecma.appticketserver.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final AttachmentRepository attachmentRepository;
    private final PlaceRepository placeRepository;

    @Override
    public ApiResult<EventResDto> getById(UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new RestException("Event not found!", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(Mapper.eventResDtoTo(event));
    }

    @Override
    public ApiResult<CustomPage<EventResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> all = eventRepository.findAll(pageable);
        CustomPage<EventResDto> eventResDtoCustomPage = mapEventResDtoCustomPage(all);
        return ApiResult.successResponse(eventResDtoCustomPage);
    }

    @Override
    public ApiResult<EventResDto> addEvent(EventReqDto eventReqDto) {
        Event event = new Event(
                eventReqDto.getName(),
                attachmentRepository.findById(eventReqDto.getBannerId()).orElseThrow(() -> new RestException("Banner not found!", HttpStatus.NOT_FOUND)),
                attachmentRepository.findById(eventReqDto.getSchemaId()).orElseThrow(() -> new RestException("Event schema not found!", HttpStatus.NOT_FOUND)),
                placeRepository.findById(eventReqDto.getPlaceId()).orElseThrow(() -> new RestException("Place not found!", HttpStatus.NOT_FOUND)),
                eventReqDto.getDescription()
        );
        eventRepository.save(event);
        return ApiResult.successResponse(Mapper.eventResDtoTo(event));
    }

    @Override
    public ApiResult<EventResDto> editEvent(EventReqDto eventDto, UUID id) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new RestException("Event not found!", HttpStatus.NOT_FOUND));
        event.setName(eventDto.getName());
        event.setBanner(attachmentRepository.findById(eventDto.getBannerId()).orElseThrow(() -> new RestException("Banner not found!", HttpStatus.NOT_FOUND)));
        event.setSchema(attachmentRepository.findById(eventDto.getSchemaId()).orElseThrow(() -> new RestException("Event schema not found!", HttpStatus.NOT_FOUND)));
        event.setPlace(placeRepository.findById(eventDto.getPlaceId()).orElseThrow(() -> new RestException("Place not found!", HttpStatus.NOT_FOUND)));
        event.setDescription(eventDto.getDescription());
        eventRepository.save(event);
        return ApiResult.successResponse(Mapper.eventResDtoTo(event));
    }

    @Override
    public ApiResult<?> deleteEvent(UUID id) {
        try {
            eventRepository.deleteById(id);
            return ApiResult.successResponse("Success deleted!");
        } catch (Exception e) {
            throw new RestException("Event not found!", HttpStatus.NOT_FOUND);
        }
    }


    private CustomPage<EventResDto> mapEventResDtoCustomPage(Page<Event> eventPage) {
        return new CustomPage<>(
                eventPage.getContent()
                        .stream()
                        .map(Mapper::eventResDtoTo)
                        .collect(Collectors.toList()),
                eventPage.getNumberOfElements(),
                eventPage.getNumber(),
                eventPage.getTotalElements(),
                eventPage.getTotalPages(),
                eventPage.getSize()
        );
    }
}
