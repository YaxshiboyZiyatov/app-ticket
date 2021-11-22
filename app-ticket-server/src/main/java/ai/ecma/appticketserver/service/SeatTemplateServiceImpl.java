package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Attachment;
import ai.ecma.appticketserver.entity.SeatTemplate;
import ai.ecma.appticketserver.entity.SeatTemplateChair;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.AttachmentRepository;
import ai.ecma.appticketserver.repository.SeatTemplateRepository;
import ai.ecma.appticketserver.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatTemplateServiceImpl implements SeatTemplateService {

    private final SeatTemplateRepository seatTemplateRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public ApiResult<SeatTemplateResDto> getById(UUID id) {
        SeatTemplate seatTemplate = seatTemplateFindById(id);
        return ApiResult.successResponse(seatTemplateResDtoTo(seatTemplate));
    }

    @Override
    public ApiResult<CustomPage<SeatTemplateResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SeatTemplate> templatePage = seatTemplateRepository.findAll(pageable);
        CustomPage<SeatTemplateResDto> seatTemplateResDtoCustomPage = mapSeatTemplateResDtoCustom(templatePage);
        return ApiResult.successResponse(seatTemplateResDtoCustomPage);
    }

    @Override
    public ApiResult<SeatTemplateResDto> create(SeatTemplateReqDto seatTemplateReqDto) {
        if (seatTemplateRepository.existsByName(seatTemplateReqDto.getName()))
            throw new RestException("Name already exists!", HttpStatus.CONFLICT);
        Attachment attachment = attachmentRepository.findById(seatTemplateReqDto.getPhotoId()).orElseThrow(() -> new RestException("Photo not found!", HttpStatus.NOT_FOUND));
        SeatTemplate seatTemplate = new SeatTemplate(seatTemplateReqDto.getName(), attachment);

        List<SeatTemplateChair> seatTemplateChairs = new ArrayList<>();

        for (SeatChairDto seatChairDto : seatTemplateReqDto.getSeatChairDtoList()) {
            SeatTemplateChair seatTemplateChair = new SeatTemplateChair(
                    seatChairDto.getSeatStatusEnum(),
                    seatChairDto.getNameSeat(),
                    seatChairDto.getPrice(),
                    seatChairDto.getRow(),
                    seatChairDto.getSection(),
                    seatTemplate
            );
            seatTemplateChairs.add(seatTemplateChair);
        }

        seatTemplate.setSeatTemplateChairs(seatTemplateChairs);
        seatTemplateRepository.save(seatTemplate);
        return ApiResult.successResponse(seatTemplateResDtoTo(seatTemplate));
    }

    @Override
    public ApiResult<SeatTemplateResDto> edit(SeatTemplateReqDto seatTemplateReqDto, UUID id) {
        if (seatTemplateRepository.existsByNameAndIdNot(seatTemplateReqDto.getName(), id))
            throw new RestException("Name already exists!", HttpStatus.CONFLICT);

        SeatTemplate seatTemplate = seatTemplateFindById(id);
        Attachment attachment = attachmentFindByID(id);
        seatTemplate.setName(seatTemplateReqDto.getName());
        seatTemplate.setSchema(attachment);
        seatTemplateRepository.save(seatTemplate);
        return ApiResult.successResponse(seatTemplateResDtoTo(seatTemplate));
    }


    @Override
    public ApiResult<?> delete(UUID id) {
        try {
            seatTemplateRepository.deleteById(id);
            return ApiResult.successResponse("Success deleting!");
        } catch (Exception e) {
            throw new RestException("Not found!", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public SeatTemplateResDto seatTemplateResDtoTo(SeatTemplate seatTemplate) {
        return new SeatTemplateResDto(
                seatTemplate.getId(),
                seatTemplate.getName(),
                !Objects.isNull(seatTemplate.getSchema())
                        ? CommonUtils.urlBuilder(seatTemplate.getSchema().getId())
                        : null
        );
    }

    public CustomPage<SeatTemplateResDto> mapSeatTemplateResDtoCustom(Page<SeatTemplate> seatTemplatePage) {
        return new CustomPage<>(
                seatTemplatePage.getContent()
                        .stream()
                        .map(this::seatTemplateResDtoTo)
                        .collect(Collectors.toList()),
                seatTemplatePage.getNumberOfElements(),
                seatTemplatePage.getNumber(),
                seatTemplatePage.getTotalElements(),
                seatTemplatePage.getTotalPages(),
                seatTemplatePage.getSize()
        );

    }

    private SeatTemplate seatTemplateFindById(UUID id) {
        return seatTemplateRepository.findById(id).orElseThrow(() -> new RestException("Seat template not found!", HttpStatus.NOT_FOUND));
    }

    private Attachment attachmentFindByID(UUID id) {
        return attachmentRepository.findById(id).orElseThrow(() -> new RestException("Photo not found!", HttpStatus.NOT_FOUND));
    }
}
