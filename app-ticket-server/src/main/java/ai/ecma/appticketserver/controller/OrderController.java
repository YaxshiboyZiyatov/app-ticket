package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.aop.CurrentUser;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping(AppConstant.ORDER_CONTROLLER)
@Tag(name = "Order controller", description = "Bu Order controller")
public interface OrderController {

    @GetMapping
    ApiResult<CustomPage<OrderResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @GetMapping("/{id}")
    ApiResult<OrderResDto> getById(@PathVariable UUID id);

    @PostMapping
    ApiResult<OrderPayDto> add(@CurrentUser @Parameter(hidden = true) User user, @RequestBody @Valid OrderReqDto orderReqDto);

    @PostMapping("/check")
    ApiResult<?> checkOrder(@RequestBody @Valid CheckOrderDto orderDto);

    @PostMapping("/finish")
    ApiResult<?> finishOrder(@CurrentUser @Parameter(hidden = true) User user, @RequestBody @Valid CheckOrderDto orderDto);

//    @PutMapping("/{id}")
//    ApiResult<OrderResDto> edit(@PathVariable UUID id);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);
}
