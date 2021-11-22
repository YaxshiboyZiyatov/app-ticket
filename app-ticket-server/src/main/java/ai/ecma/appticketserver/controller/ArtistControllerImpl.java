package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.ArtistReqDto;
import ai.ecma.appticketserver.payload.ArtistResDto;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.service.ArtistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ArtistControllerImpl implements ArtistController {

    private final ArtistService artistService;

    @Override
    public ApiResult<ArtistResDto> getById(UUID id) {
        return artistService.getById(id);
    }

    @Override
    public ApiResult<CustomPage<ArtistResDto>> getAll(int page, int size) {
        return artistService.getAll(page, size);
    }

    @PreAuthorize("hasAuthority('ADD_ARTIST')")
    @Override
    public ApiResult<ArtistResDto> addArtist(ArtistReqDto artistReqDto) {
        log.info("Artist add qilishga keldi: {}", artistReqDto);
        ApiResult<ArtistResDto> result = artistService.addArtist(artistReqDto);
        log.info("Artist add qildi: {}", result.getData());
        return result;
    }

    @PreAuthorize("hasAuthority('EDIT_ARTIST')")
    @Override
    public ApiResult<ArtistResDto> editArtist(ArtistReqDto artistReqDto, UUID id) {
        return artistService.editArtist(artistReqDto, id);
    }

    @PreAuthorize("hasAuthority('DELETE_ARTIST')")
    @Override
    public ApiResult<?> deleteArtist(UUID id) {
        return artistService.deleteArtist(id);
    }
}
