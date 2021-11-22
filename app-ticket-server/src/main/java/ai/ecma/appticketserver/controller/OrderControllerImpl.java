package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    public ApiResult<CustomPage<OrderResDto>> getAll(int page, int size) {
        return orderService.getAll(page, size);
    }

    @Override
    public ApiResult<OrderResDto> getById(UUID id) {
        return orderService.getById(id);
    }

    @Override
    public ApiResult<OrderPayDto> add(User user, OrderReqDto orderReqDto) {
        return orderService.add(user, orderReqDto);
    }

    @Override
    public ApiResult<?> checkOrder(CheckOrderDto orderDto) {
        return orderService.checkTicket(orderDto);
    }

    @Override
    public ApiResult<?> finishOrder(User currentUser, CheckOrderDto orderDto) {
        return orderService.finish(currentUser, orderDto);
    }

    @Override
    public ApiResult<?> delete(UUID id) {
        return orderService.delete(id);
    }
}
