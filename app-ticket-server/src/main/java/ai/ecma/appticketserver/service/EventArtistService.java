package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.EventArtist;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.EventArtistReqDto;
import ai.ecma.appticketserver.payload.EventArtistResDto;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface EventArtistService {


    ApiResult<EventArtistResDto> getById(UUID id);

    ApiResult<CustomPage<EventArtistResDto>> getAll(int page, int size);

    ApiResult<EventArtistResDto> addEventArtist(EventArtistReqDto eventArtistReqDto);

    ApiResult<EventArtistResDto> editEventArtist(EventArtistReqDto eventArtistReqDto, UUID id);

    ApiResult<?> deleteEventArtist(UUID id);

}
