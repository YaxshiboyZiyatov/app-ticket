package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.service.PaymentReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
public class PaymentReturnControllerImpl implements PaymentReturnController {

    private final PaymentReturnService paymentReturnService;

    @PreAuthorize("hasAuthority('VIEW_PAYBACK_INFO')")
    @Override
    public ApiResult<CustomPage<PaymentReturnResDto>> getAll(int page, int size) {
        return paymentReturnService.getAll(page, size);
    }

    @PreAuthorize("hasAuthority('VIEW_PAYBACK_INFO')")
    @Override
    public ApiResult<PaymentReturnResDto> get(UUID id) {
        return paymentReturnService.get(id);
    }

    @PreAuthorize("hasAuthority('VIEW_PAYBACK_INFO')")
    @Override
    public ApiResult<CustomPage<PaymentReturnResDto>> returnPaymentByEventSum(UUID id, int page, int size) {
        return paymentReturnService.returnPaymentByEventSum(id, page, size);
    }

    //    @PreAuthorize("hasAuthority('VIEW_PAYBACK_INFO')")
    @Override
    public ApiResult<?> cancelBron(BronReturnOrderDto bronReturnOrderDto) {
        return paymentReturnService.cancelBron(bronReturnOrderDto);
    }

//    @PreAuthorize("hasAuthority('VIEW_PAYMENT_RETURN')")
    @Override
    public ApiResult<?> ticketPaymentReturn(TicketReturnOrderDto ticketReturnOrderDto) {
        return paymentReturnService.ticketPaymentReturn(ticketReturnOrderDto);

    }

    @Override
    public ApiResult<?> ticketPaymentPaymeReturn(TicketReturnOrderDto ticketReturnOrderDto) {
        return null;
    }

    @Override
    public ApiResult<?> cancelTickets(TicketCancelDto ticketCancelDto, User user) {
        return paymentReturnService.cancelTickets(ticketCancelDto, user);
    }

    @PreAuthorize("hasAuthority('VIEW_PAYMENT_RETURN')")
    @Override
    public ApiResult<PaymentReportResDto> reportCancel(DateReqDto dateReqDto) {

        return paymentReturnService.reportCancel(dateReqDto);

    }
}
