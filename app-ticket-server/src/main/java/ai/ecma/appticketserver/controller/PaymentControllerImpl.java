package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.service.PaymeService;
import ai.ecma.appticketserver.service.PaymentService;
import ai.ecma.appticketserver.utils.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PaymentControllerImpl implements PaymentController {
    private final PaymeService paymeService;
    private final PaymentService paymentService;

    @PreAuthorize("hasAuthority('VIEW_PAYMENT')")
    @Override
    public ApiResult<CustomPage<PaymentResDto>> getAll(int page, int size) {
        return null;
    }

    @PreAuthorize("hasAuthority('VIEW_PAYMENT')")
    @Override
    public ApiResult<PaymentResDto> getById(UUID id) {
        return null;
    }

    @PreAuthorize("hasAuthority('EDIT_PAYMENT_PAYMENT')")
    @Override
    public ApiResult<PaymentResDto> add(PaymentReqDto paymentReqDto) {
        return null;
    }

    @PreAuthorize("hasAuthority('EDIT_PAYMENT_PAYMENT')")
    @Override
    public ApiResult<PaymentResDto> edit(UUID id, PaymentReqDto paymentReqDto) {
        return null;
    }

    @PreAuthorize("hasAuthority('DELETE_PAYMENT')")
    @Override
    public ApiResult<?> delete(UUID id) {
        return null;
    }


    @PreAuthorize("hasAuthority('VIEW_PAYMENT')")
    @Override
    public ApiResult<PaymentReportResDto> report(DateReqDto dateReqDto ) {
        return paymentService.report(dateReqDto);
    }
}
