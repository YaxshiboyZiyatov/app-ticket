package ai.ecma.appticketserver.service;


import ai.ecma.appticketserver.entity.BronTariff;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.BronTariffReqDto;
import ai.ecma.appticketserver.payload.BronTariffResDto;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.repository.BronTariffRepository;
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
public class BronTariffServiceImpl implements BronTariffService {

    final BronTariffRepository bronTariffRepository;


    @Override
    public ApiResult<CustomPage<BronTariffResDto>> getAll(int page, int size) {
        //.com.api?page=0&size=10
        Pageable pageable = PageRequest.of(page, size);
        Page<BronTariff> bronTariffPage = bronTariffRepository.findAll(pageable);
        CustomPage<BronTariffResDto> customPage = mapBronTariffDtoPage(bronTariffPage);
        return ApiResult.successResponse(customPage);
    }

    @Override
    public ApiResult<BronTariffResDto> get(UUID id) {
        BronTariff bronTariff = bronTariffRepository.findById(id).orElseThrow(() -> new RestException("BronTariff not found",
                HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(Mapper.bronTariffResDtoTo(bronTariff));
    }

    @Override
    public ApiResult<BronTariffResDto> create(BronTariffReqDto bronTariffReqDto) {

            BronTariff bronTariff = new BronTariff(
                    bronTariffReqDto.getLifetime(),
                    bronTariffReqDto.getPercent(),
                    bronTariffReqDto.isDisabled()
            );
            bronTariffRepository.save(bronTariff);
            return ApiResult.successResponse(Mapper.bronTariffResDtoTo(bronTariff));
    }

    @Override
    public ApiResult<BronTariffResDto> edit(BronTariffReqDto bronTariffReqDto, UUID id) {

        BronTariff bronTariff =  bronTariffRepository.findById(id).orElseThrow(() -> new RestException("Bron tariff not found",
                HttpStatus.NOT_FOUND));

        if (bronTariffReqDto.getLifetime() != null) {
            bronTariff.setLifetime(bronTariffReqDto.getLifetime());
        }
        if (bronTariffReqDto.getPercent() != null) {
            bronTariff.setPercent(bronTariffReqDto.getPercent());
        }
        bronTariff.setDisabled(bronTariffReqDto.isDisabled());

        BronTariff save = bronTariffRepository.save(bronTariff);
        return new ApiResult<>(true, "", Mapper.bronTariffResDtoTo(save));

    }

    @Override
    public ApiResult<?> deleteAll() {
        bronTariffRepository.deleteAll();
        return new ApiResult<>(true, "", null);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        bronTariffRepository.deleteById(id);
        return new ApiResult<>(true, "Bunday id li Bron-tariff o'chirildi", null);
    }


    private CustomPage<BronTariffResDto> mapBronTariffDtoPage(Page<BronTariff> bronTariffPage) {
        CustomPage<BronTariffResDto> customPage = new CustomPage<>(
                bronTariffPage.getContent()
                        .stream()
                        .map(Mapper::bronTariffResDtoTo)
                        .collect(Collectors.toList()),
                bronTariffPage.getNumberOfElements(),
                bronTariffPage.getNumber(),
                bronTariffPage.getTotalElements(),
                bronTariffPage.getTotalPages(),
                bronTariffPage.getSize()
        );
        return customPage;
    }
}
