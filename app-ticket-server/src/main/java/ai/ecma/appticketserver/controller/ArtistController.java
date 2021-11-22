package ai.ecma.appticketserver.controller;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.ArtistReqDto;
import ai.ecma.appticketserver.payload.ArtistResDto;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RequestMapping(AppConstant.ARTIST_CONTROLLER)
@Tag(name = "Artist controller", description = "Bu artist controller")
public interface ArtistController {

    @GetMapping("/{id}")
    ApiResult<ArtistResDto> getById(@PathVariable UUID id);

    @GetMapping
    ApiResult<CustomPage<ArtistResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @PostMapping
    ApiResult<ArtistResDto> addArtist(@RequestBody ArtistReqDto artistReqDto);

    @PutMapping("/{id}")
    ApiResult<ArtistResDto> editArtist(@RequestBody ArtistReqDto artistReqDto, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    ApiResult<?> deleteArtist(@PathVariable UUID id);

}
