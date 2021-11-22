package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.ArtistReqDto;
import ai.ecma.appticketserver.payload.ArtistResDto;
import ai.ecma.appticketserver.payload.CustomPage;

import java.util.UUID;

public interface ArtistService {

    ApiResult<ArtistResDto> getById(UUID id);

    ApiResult<CustomPage<ArtistResDto>> getAll(int page, int size);

    ApiResult<ArtistResDto> addArtist(ArtistReqDto artistReqDto);

    ApiResult<ArtistResDto> editArtist(ArtistReqDto artistReqDto, UUID id);

    ApiResult<?> deleteArtist(UUID id);

}
