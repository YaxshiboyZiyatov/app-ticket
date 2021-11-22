package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.PayType;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.PayTypeReqDto;
import ai.ecma.appticketserver.payload.PayTypeResDto;
import ai.ecma.appticketserver.repository.PayTypeRepository;
import ai.ecma.appticketserver.utils.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PayTypeServiceImpl implements PayTypeService {
    final PayTypeRepository payTypeRepository;

    @Override
    public ApiResult<List<PayTypeResDto>> getAll() {
        List<PayType> all = payTypeRepository.findAll();
        List<PayTypeResDto> collect = all.stream().map(Mapper::payTypeResDto).collect(Collectors.toList());
        return new ApiResult<>(true, "", collect);
    }

    @Override
    public ApiResult<PayTypeResDto> get(UUID id) {
        Optional<PayType> optionalPayType = payTypeRepository.findById(id);
        if (optionalPayType.isPresent()) {
            PayType payType = optionalPayType.get();
            return new ApiResult<>(true, "", Mapper.payTypeResDto(payType));
        }
        return new ApiResult<>(false, "Pay type not found", null);
    }

    @Override
    public ApiResult<PayTypeResDto> create(PayTypeReqDto payTypeDto) {
        PayType payType = payTypeRepository.save(
                new PayType(
                        payTypeDto.getName(),
                        payTypeDto.getPayTypeEnum()
                )
        );
        return ApiResult.successResponse(Mapper.payTypeResDto(payType));
    }

    @Override
    public ApiResult<PayTypeResDto> edit(PayTypeReqDto payTypeDto, UUID id) {
        return null;
    }

    @Override
    public ApiResult<?> deleteAll() {
        return null;
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        payTypeRepository.deleteById(id);
        return null;
    }
}
