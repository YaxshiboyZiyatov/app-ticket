package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(AppConstant.PAYMENT_CONTROLLER)
@Tag(name = "Payment controller", description = "Bu payment controller")
public interface PaymentController {

    @GetMapping
    ApiResult<CustomPage<PaymentResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @GetMapping("/{id}")
    ApiResult<PaymentResDto> getById(@PathVariable UUID id);

    @PostMapping
    ApiResult<PaymentResDto> add(@RequestBody @Valid PaymentReqDto paymentReqDto);

    @PutMapping("/{id}")
    ApiResult<PaymentResDto> edit(@PathVariable UUID id, @RequestBody @Valid PaymentReqDto paymentReqDto);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

    @PostMapping("/report")
    ApiResult<PaymentReportResDto> report(@RequestBody @Valid DateReqDto dateReqDto );

}
