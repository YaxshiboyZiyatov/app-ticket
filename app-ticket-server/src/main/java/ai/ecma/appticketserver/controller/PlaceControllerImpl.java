package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.PlaceReqDto;
import ai.ecma.appticketserver.payload.PlaceResDto;
import ai.ecma.appticketserver.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
@RestController
public class PlaceControllerImpl implements PlaceController{
    @Autowired
    PlaceService placeService;

    @Override
    public ApiResult<CustomPage<PlaceResDto>> getAll(int page, int size) {
        return placeService.getAll(page, size);
    }

    @Override
    public ApiResult<PlaceResDto> get(UUID id) {
        return placeService.get(id);
    }

    @Override
    public ApiResult<PlaceResDto> creat(PlaceReqDto placeReqDto) {
        return placeService.creat(placeReqDto);
    }

    @Override
    public ApiResult<PlaceResDto> edit(UUID id, PlaceReqDto placeReqDto) {
        return placeService.edit(id, placeReqDto);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return placeService.delete(id);
    }

    @Override
    public ApiResult<?> deleteAll() {
        return placeService.deleteAll();
    }
}
