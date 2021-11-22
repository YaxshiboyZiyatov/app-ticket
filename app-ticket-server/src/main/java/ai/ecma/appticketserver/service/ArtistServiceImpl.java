package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Artist;
import ai.ecma.appticketserver.entity.Attachment;
import ai.ecma.appticketserver.entity.Role;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.ArtistRepository;
import ai.ecma.appticketserver.repository.AttachmentRepository;
import ai.ecma.appticketserver.utils.CommonUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityListeners;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {
    private final ArtistRepository artistRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public ApiResult<ArtistResDto> getById(UUID id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new RestException("Artist not found!", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(artistResDtoTo(artist));
    }

    @Override
    public ApiResult<CustomPage<ArtistResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Artist> all = artistRepository.findAll(pageable);
        CustomPage<ArtistResDto> artistResDtoCustomPage = mapArtistResDtoCustomPage(all);
        return ApiResult.successResponse(artistResDtoCustomPage);
    }

    @Override
    public ApiResult<ArtistResDto> addArtist(ArtistReqDto artistReqDto) {
        Attachment attachment = attachmentRepository.findById(artistReqDto.getPhotoId()).orElseThrow(() -> new RestException("Photo not found!", HttpStatus.NOT_FOUND));
        Artist artist = new Artist(
                artistReqDto.getName(),
                artistReqDto.getDescription(),
                attachment
        );
        artistRepository.save(artist);
        return ApiResult.successResponse(artistResDtoTo(artist));
    }

    @Override
    public ApiResult<ArtistResDto> editArtist(ArtistReqDto artistReqDto, UUID id) {
        Artist artist = artistRepository.findById(id).orElseThrow(()
                -> new RestException("Artist not found!", HttpStatus.NOT_FOUND));
        artist.setName(artistReqDto.getName());
        artist.setDescription(artist.getDescription());
        Attachment attachment = attachmentRepository.findById(artistReqDto.getPhotoId()).orElseThrow(()
                -> new RestException("Photo not found!", HttpStatus.NOT_FOUND));
        artist.setPhoto(attachment);
        artistRepository.save(artist);
        return ApiResult.successResponse(artistResDtoTo(artist));
    }

    @Override
    public ApiResult<?> deleteArtist(UUID id) {
        try {
            artistRepository.deleteById(id);
            return ApiResult.successResponse("Success deleted!");

        } catch (Exception e) {
            throw new RestException("Artist not found!", HttpStatus.NOT_FOUND);
        }
    }

    public ArtistResDto artistResDtoTo(Artist artist) {

        return new ArtistResDto(
                artist.getId(),
                artist.getName(),
                artist.getDescription(),
                !Objects.isNull(artist.getPhoto())
                        ? CommonUtils.urlBuilder(artist.getPhoto().getId())
                        : null
        );
    }

    private CustomPage<ArtistResDto> mapArtistResDtoCustomPage(Page<Artist> artistPage) {
        CustomPage<ArtistResDto> customPage = new CustomPage<>(
                artistPage.getContent()
                        .stream()
                        .map(this::artistResDtoTo)
                        .collect(Collectors.toList()),
                artistPage.getNumberOfElements(),
                artistPage.getNumber(),
                artistPage.getTotalElements(),
                artistPage.getTotalPages(),
                artistPage.getSize()
        );
        return customPage;
    }
}
