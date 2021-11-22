package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Payment;
import ai.ecma.appticketserver.exception.RestException;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.repository.PaymentRepository;
import ai.ecma.appticketserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResult<CustomPage<PaymentResDto>> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Payment> all = paymentRepository.findAll(pageable);
        CustomPage<PaymentResDto> paymentResDtoCustomPage = mapResDtoCustomPage(all);
        return ApiResult.successResponse(paymentResDtoCustomPage);

    }

    @Override
    public ApiResult<PaymentResDto> getById(UUID id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RestException("Payment not found!", HttpStatus.NOT_FOUND));
        return ApiResult.successResponse(paymentResDtoTo(payment));
    }

    @Override
    public ApiResult<PaymentResDto> add(PaymentReqDto paymentReqDto) {

        Payment payment = new Payment(
                paymentReqDto.getAmount(),
                paymentReqDto.getPayType(),
                userRepository.findById(paymentReqDto.getUser()).orElseThrow(() -> new RestException("User not found!", HttpStatus.NOT_FOUND))
        );
        paymentRepository.save(payment);
        return ApiResult.successResponse(paymentResDtoTo(payment));
    }

    @Override
    public ApiResult<PaymentResDto> edit(UUID id, PaymentReqDto paymentReqDto) {
        return null;
    }

    @Override
    public ApiResult<?> delete(UUID id) {

        try {
            paymentRepository.deleteById(id);
            return ApiResult.successResponse("Success deleting!");
        } catch (Exception e) {
            throw new RestException("Payment not found", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public PaymentResDto paymentResDtoTo(Payment payment) {
        return new PaymentResDto(
                payment.getId(),
                payment.getAmount(),
                payment.getPayType(),
                !Objects.isNull(payment.getUser()) ? payment.getUser().getId() : null
        );
    }

    @Override
    public ApiResult<PaymentReportResDto> report(DateReqDto dateReqDto) {

        List<Payment> paymentList = paymentRepository.findAllByCreatedAtBetween(dateReqDto.getTimestampOne(), dateReqDto.getTimestampTwo());
        double summAmount = 0;
        for (Payment payment : paymentList) {
            summAmount += payment.getAmount();
        }
//        PaymentReportResDto paymentReportResDto = new PaymentReportResDto(
//                "Sotilgan biletlarni umumiy summasi",
//                summAmount);
        return ApiResult.successResponse();
    }

    private CustomPage<PaymentResDto> mapResDtoCustomPage(Page<Payment> paymentPage) {
        return new CustomPage<>(
                paymentPage.getContent()
                        .stream()
                        .map(this::paymentResDtoTo)
                        .collect(Collectors.toList()),
                paymentPage.getNumberOfElements(),
                paymentPage.getNumber(),
                paymentPage.getTotalElements(),
                paymentPage.getTotalPages(),
                paymentPage.getSize()
        );
    }

}
