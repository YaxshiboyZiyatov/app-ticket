package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.SeatTemplate;
import ai.ecma.appticketserver.entity.SeatTemplateChair;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.SeatTemplateChairReqDto;
import ai.ecma.appticketserver.payload.SeatTemplateChairResDto;
import ai.ecma.appticketserver.repository.SeatTemplateChairRepository;
import ai.ecma.appticketserver.repository.SeatTemplateRepository;
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
public class SeatTemplateChairServiceImpl implements SeatTemplateChairService {

    private final SeatTemplateChairRepository seatTemplateChairRepository;
    private final SeatTemplateRepository seatTemplateRepository;
    private final SeatTemplateService seatTemplateService;

    private SeatTemplateChair chairFindById(UUID id) {
        return seatTemplateChairRepository.findById(id).orElseThrow(() -> new RestException("Seat template chair not found", HttpStatus.NOT_FOUND));
    }

    private SeatTemplate seatTemplateFindById(UUID id) {
        return seatTemplateRepository.findById(id).orElseThrow(() -> new RestException("Seat template not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public ApiResult<SeatTemplateChairResDto> getById(UUID id) {
        SeatTemplateChair seatTemplateChair = chairFindById(id);
        return ApiResult.successResponse(seatTemplateChairResDtoTo(seatTemplateChair));
    }

    @Override
    public ApiResult<CustomPage<SeatTemplateChairResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SeatTemplateChair> all = seatTemplateChairRepository.findAll(pageable);
        CustomPage<SeatTemplateChairResDto> seatTemplateChairResDtoCustomPage = mapTemplateResDtoCustomPage(all);
        return ApiResult.successResponse(seatTemplateChairResDtoCustomPage);
    }

    @Override
    public ApiResult<SeatTemplateChairResDto> create(SeatTemplateChairReqDto seatTemplateChairReqDto) {
        SeatTemplate seatTemplate = seatTemplateFindById(seatTemplateChairReqDto.getSeatTemplateId());

//        boolean b = seatTemplateChairRepository.existsByNameSeatAndRowAndSectionAndSeatTemplateId(
//                seatTemplateChairReqDto.getNameSeat(),
//                seatTemplateChairReqDto.getRow(),
//                seatTemplateChairReqDto.getSection(),
//                seatTemplate.getId()
//        );

//        if (b) throw new RestException("This seat template chair already exists", HttpStatus.CONFLICT);

        SeatTemplateChair seatTemplateChair = new SeatTemplateChair(
                seatTemplateChairReqDto.getSeatStatusEnum(),
                seatTemplateChairReqDto.getNameSeat(),
                seatTemplateChairReqDto.getPrice(),
                seatTemplateChairReqDto.getRow(),
                seatTemplateChairReqDto.getSection(),
                seatTemplate
        );

        seatTemplateChairRepository.save(seatTemplateChair);

        return ApiResult.successResponse(seatTemplateChairResDtoTo(seatTemplateChair));
    }

    @Override
    public ApiResult<SeatTemplateChairResDto> edit(SeatTemplateChairReqDto seatTemplateChairReqDto, UUID id) {

        SeatTemplateChair seatTemplateChair = chairFindById(id);

        SeatTemplate seatTemplate = seatTemplateFindById(seatTemplateChairReqDto.getSeatTemplateId());

//        boolean b = seatTemplateChairRepository.existsByNameSeatAndRowAndSectionAndSeatTemplateIdAndIdNot(
//                seatTemplateChairReqDto.getNameSeat(),
//                seatTemplateChairReqDto.getRow(),
//                seatTemplateChairReqDto.getSection(),
//                seatTemplate.getId(),
//                id
//        );
//
//        if (b) throw new RestException("Already exists", HttpStatus.CONFLICT);


        seatTemplateChair.setSeatStatusEnum(seatTemplateChairReqDto.getSeatStatusEnum());
        seatTemplateChair.setNameSeat(seatTemplateChairReqDto.getNameSeat());
        seatTemplateChair.setPrice(seatTemplateChairReqDto.getPrice());
        seatTemplateChair.setRow(seatTemplateChairReqDto.getRow());
        seatTemplateChair.setSection(seatTemplateChairReqDto.getSection());
        seatTemplateChair.setSeatTemplate(seatTemplate);

        seatTemplateChairRepository.save(seatTemplateChair);
        return ApiResult.successResponse(seatTemplateChairResDtoTo(seatTemplateChair));

    }

    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            seatTemplateChairRepository.deleteById(id);
            return ApiResult.successResponse("Success deleted!");
        } catch (Exception e) {
            throw new RestException("Not found!", HttpStatus.NOT_FOUND);
        }
    }

    public SeatTemplateChairResDto seatTemplateChairResDtoTo(SeatTemplateChair seatTemplateChair) {
        return new SeatTemplateChairResDto(
                seatTemplateChair.getId(),
                seatTemplateChair.getSeatStatusEnum(),
                seatTemplateChair.getNameSeat(),
                seatTemplateChair.getPrice(),
                seatTemplateChair.getRow(),
                seatTemplateChair.getSection(),
                seatTemplateService.seatTemplateResDtoTo(seatTemplateChair.getSeatTemplate())
        );
    }

    private CustomPage<SeatTemplateChairResDto> mapTemplateResDtoCustomPage(Page<SeatTemplateChair> seatTemplateChairPage) {
        return new CustomPage<>(
                seatTemplateChairPage.getContent()
                        .stream()
                        .map(this::seatTemplateChairResDtoTo)
                        .collect(Collectors.toList()),
                seatTemplateChairPage.getNumberOfElements(),
                seatTemplateChairPage.getNumber(),
                seatTemplateChairPage.getTotalElements(),
                seatTemplateChairPage.getTotalPages(),
                seatTemplateChairPage.getSize()

        );
    }


}
