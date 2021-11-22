package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Payment;
import ai.ecma.appticketserver.payload.*;

import java.util.UUID;

public interface PaymentService {

    ApiResult<CustomPage<PaymentResDto>> getAll(int page, int size);

    ApiResult<PaymentResDto> getById(UUID id);

    ApiResult<PaymentResDto> add(PaymentReqDto paymentReqDto);

    ApiResult<PaymentResDto> edit(UUID id, PaymentReqDto paymentReqDto);

    ApiResult<?> delete(UUID id);

    PaymentResDto paymentResDtoTo(Payment payment);

    ApiResult<PaymentReportResDto> report(DateReqDto dateReqDto );
}
