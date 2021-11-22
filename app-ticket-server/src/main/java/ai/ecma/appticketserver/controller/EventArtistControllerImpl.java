package ai.ecma.appticketserver.controller;


import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.EventArtistReqDto;
import ai.ecma.appticketserver.payload.EventArtistResDto;
import ai.ecma.appticketserver.service.EventArtistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EventArtistControllerImpl implements EventArtistController {
    private final EventArtistService eventArtistService;
    @PreAuthorize("hasAuthority('ADD_ARTIST')")
    @Override
    public ApiResult<EventArtistResDto> getById(UUID id) {
        return eventArtistService.getById(id);
    }
    @PreAuthorize("hasAuthority('ADD_ARTIST')")
    @Override
    public ApiResult<CustomPage<EventArtistResDto>> getAll(int page, int size) {
        return eventArtistService.getAll(page,size);
    }
    @PreAuthorize("hasAuthority('ADD_ARTIST')")
    @Override
    public ApiResult<EventArtistResDto> addArtist(EventArtistReqDto eventArtistReqDto) {
        return eventArtistService.addEventArtist(eventArtistReqDto);
    }
    @PreAuthorize("hasAuthority('ADD_ARTIST')")
    @Override
    public ApiResult<EventArtistResDto> editArtist(EventArtistReqDto eventArtistReqDto, UUID id) {
        return eventArtistService.editEventArtist(eventArtistReqDto,id);
    }
    @PreAuthorize("hasAuthority('ADD_ARTIST')")
    @Override
    public ApiResult<?> deleteArtist(UUID id) {
        return eventArtistService.deleteEventArtist(id);
    }
}
