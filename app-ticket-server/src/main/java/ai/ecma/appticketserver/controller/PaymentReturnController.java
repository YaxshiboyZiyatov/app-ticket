package ai.ecma.appticketserver.controller;


import ai.ecma.appticketserver.aop.CurrentUser;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(AppConstant.PAYMENT_RETURN_CONTROLLER)
@Tag(name = "PaymentReturn controller", description = "Bu PaymentReturn controller")
public interface PaymentReturnController {

    @GetMapping
    ApiResult<CustomPage<PaymentReturnResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @GetMapping("/{id}")
    ApiResult<PaymentReturnResDto> get(@PathVariable UUID id);

    @GetMapping("returnPaymentByEventSum/{id}")
    ApiResult<CustomPage<PaymentReturnResDto>> returnPaymentByEventSum(@PathVariable UUID id, @RequestParam int page, @RequestParam int size);

    @PutMapping("/cancel-bron")
    ApiResult<?> cancelBron(@RequestBody @Valid BronReturnOrderDto bronReturnOrderDto);

    @PostMapping("/ticket-payment-return")
    ApiResult<?> ticketPaymentReturn(@RequestBody @Valid TicketReturnOrderDto ticketReturnOrderDto);

    @PostMapping("ticket-payment-payme-return")
    ApiResult<?> ticketPaymentPaymeReturn(@RequestBody @Valid TicketReturnOrderDto ticketReturnOrderDto);

    @PostMapping("ticket-payment-cancel-return")
    ApiResult<?> cancelTickets(@RequestBody @Valid TicketCancelDto ticketCancelDto, @CurrentUser @Parameter(hidden = true) User user);

    @GetMapping("report-cancel-amount")
    ApiResult<PaymentReportResDto> reportCancel(@RequestBody DateReqDto dateReqDto);

}
