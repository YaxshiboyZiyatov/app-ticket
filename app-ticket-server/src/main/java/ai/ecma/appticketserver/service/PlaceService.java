package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Place;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.PlaceReqDto;
import ai.ecma.appticketserver.payload.PlaceResDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public interface PlaceService {
    ApiResult<CustomPage<PlaceResDto>> getAll(int page, int size);

    ApiResult<PlaceResDto> get(UUID id);

    ApiResult<PlaceResDto> creat(PlaceReqDto placeReqDto);

    ApiResult<PlaceResDto> edit(UUID id, PlaceReqDto placeReqDto);

    ApiResult<?> delete(UUID id);

    ApiResult<?> deleteAll();
}
