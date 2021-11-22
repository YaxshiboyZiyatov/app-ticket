package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Artist;
import ai.ecma.appticketserver.entity.Event;
import ai.ecma.appticketserver.entity.EventArtist;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.ArtistRepository;
import ai.ecma.appticketserver.repository.EventArtistRepository;
import ai.ecma.appticketserver.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventArtistServiceImpl implements EventArtistService {
    private final EventArtistRepository eventArtistRepository;
    private final EventRepository eventRepository;
    private final ArtistRepository artistRepository;

    @Override
    public ApiResult<EventArtistResDto> getById(UUID id) {
        EventArtist artist = eventArtistRepository.findById(id).orElseThrow(() -> new RestException("Event Artist not fount", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(eventArtistResDtoMapper(artist));
    }

    @Override
    public ApiResult<CustomPage<EventArtistResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EventArtist> artists = eventArtistRepository.findAll(pageable);
        CustomPage<EventArtistResDto> customPage = mapEventArtistResDtoCustomPage(artists);
        return ApiResult.successResponse(customPage);

    }

    @Override
    public ApiResult<EventArtistResDto> addEventArtist(EventArtistReqDto eventArtistReqDto) {
        Artist artist = artistRepository.findById(eventArtistReqDto.getArtistId()).orElseThrow(() -> new RestException("Artist not fount", HttpStatus.NOT_FOUND));
        Event event = eventRepository.findById(eventArtistReqDto.getEventId()).orElseThrow(() -> new RestException("Event not fount", HttpStatus.NOT_FOUND));
        EventArtist eventArtist = new EventArtist(event, artist, eventArtistReqDto.isMainArtist());
        eventArtistRepository.save(eventArtist);
        return ApiResult.successResponse(eventArtistResDtoMapper(eventArtist));
    }

    @Override
    public ApiResult<EventArtistResDto> editEventArtist(EventArtistReqDto eventArtistReqDto, UUID id) {
        EventArtist eventArtist = eventArtistRepository.findById(id).orElseThrow(() -> new RestException("Event Artist not fount", HttpStatus.NOT_FOUND));
        Artist artist = artistRepository.findById(eventArtistReqDto.getArtistId()).orElseThrow(() -> new RestException("Artist not fount", HttpStatus.NOT_FOUND));
        Event event = eventRepository.findById(eventArtistReqDto.getEventId()).orElseThrow(() -> new RestException("Event not fount", HttpStatus.NOT_FOUND));
        eventArtist.setArtist(artist);
        eventArtist.setEvent(event);
        eventArtist.setMainArtist(eventArtist.isMainArtist());
        eventArtistRepository.save(eventArtist);
        return ApiResult.successResponse(eventArtistResDtoMapper(eventArtist));
    }

    @Override
    public ApiResult<?> deleteEventArtist(UUID id) {
        eventArtistRepository.findById(id).orElseThrow(() -> new RestException("Event Artist not fount", HttpStatus.NOT_FOUND));
        eventArtistRepository.deleteById(id);
        return ApiResult.failResponse("EventArtist deleted");
    }

    private EventArtistResDto eventArtistResDtoMapper(EventArtist eventArtist) {
        return new EventArtistResDto(eventArtist.getId(), eventArtist.getEvent(), eventArtist.getArtist(), eventArtist.isMainArtist());
    }


    private CustomPage<EventArtistResDto> mapEventArtistResDtoCustomPage(Page<EventArtist> eventArtistPage) {
        return new CustomPage<>(
                eventArtistPage.getContent()
                        .stream()
                        .map(this::eventArtistResDtoMapper)
                        .collect(Collectors.toList()),
                eventArtistPage.getNumberOfElements(),
                eventArtistPage.getNumber(),
                eventArtistPage.getTotalElements(),
                eventArtistPage.getTotalPages(),
                eventArtistPage.getSize()
        );


    }
}
