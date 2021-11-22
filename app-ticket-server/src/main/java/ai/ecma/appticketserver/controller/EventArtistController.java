package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(AppConstant.EVENT_ARTIST_CONTROLLER)
@Tag(name = "EventArtist controller", description = "Bu EventArtist controller")
public interface EventArtistController {

    @GetMapping("/{id}")
    ApiResult<EventArtistResDto> getById(@PathVariable UUID id);

    @GetMapping
    ApiResult<CustomPage<EventArtistResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @PostMapping
    ApiResult<EventArtistResDto> addArtist(@RequestBody @Valid EventArtistReqDto eventArtistReqDto);

    @PutMapping("/{id}")
    ApiResult<EventArtistResDto> editArtist(@RequestBody @Valid  EventArtistReqDto eventArtistReqDto, @PathVariable UUID id);

    @DeleteMapping("/{id}")
    ApiResult<?> deleteArtist(@PathVariable UUID id);

}
