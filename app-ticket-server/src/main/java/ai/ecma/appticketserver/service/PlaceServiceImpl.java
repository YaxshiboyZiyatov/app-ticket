package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Place;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.PlaceReqDto;
import ai.ecma.appticketserver.payload.PlaceResDto;
import ai.ecma.appticketserver.repository.PlaceRepository;
import ai.ecma.appticketserver.utils.Mapper;
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
public class PlaceServiceImpl implements PlaceService {
    final PlaceRepository placeRepository;

    @Override
    public ApiResult<CustomPage<PlaceResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Place> placePage = placeRepository.findAll(pageable);
        CustomPage<PlaceResDto> customPage = mapPlaceResDtoCustomPage(placePage);
        return ApiResult.successResponse(customPage);
    }

    @Override
    public ApiResult<PlaceResDto> get(UUID id) {
        Place place = placeRepository.findById(id).orElseThrow(() -> new RestException("Place not found!", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(Mapper.placeResToDto(place));

    }

    @Override
    public ApiResult<PlaceResDto> creat(PlaceReqDto placeReqDto) {
        Place place = new Place();
        place.setAddress(placeReqDto.getAddress());
        place.setLat(placeReqDto.getLat());
        place.setLon(placeReqDto.getLon());
        placeRepository.save(place);
        return ApiResult.successResponse("Place is saved", Mapper.placeResToDto(place));
    }

    @Override
    public ApiResult<PlaceResDto> edit(UUID id, PlaceReqDto placeReqDto) {
        Place place = placeRepository.findById(id).orElseThrow(() -> new RestException("Role not found", HttpStatus.NOT_FOUND));
        place.setAddress(placeReqDto.getAddress());
        place.setLat(placeReqDto.getLat());
        place.setLon(placeReqDto.getLon());
        placeRepository.save(place);
        return ApiResult.successResponse("Success edited", Mapper.placeResToDto(place));
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            placeRepository.deleteById(id);
            return ApiResult.successResponse("Success deleted");
        } catch (Exception e) {
            throw new RestException("Place not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ApiResult<?> deleteAll() {
        placeRepository.deleteAll();
        return ApiResult.successResponse("Success deleted All");
    }


    private CustomPage<PlaceResDto> mapPlaceResDtoCustomPage(Page<Place> placePage) {
        return new CustomPage<>(
                placePage.getContent()
                        .stream()
                        .map(Mapper::placeResToDto)
                        .collect(Collectors.toList()),
                placePage.getNumberOfElements(),
                placePage.getNumber(),
                placePage.getTotalElements(),
                placePage.getTotalPages(),
                placePage.getSize()
        );
    }

}
