package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.payload.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

public interface OrderService {

    ApiResult<CustomPage<OrderResDto>> getAll(int page, int size);

    ApiResult<OrderResDto> getById(UUID id);

    ApiResult<OrderPayDto> add(User user, OrderReqDto orderReqDto);

    ApiResult<?> checkTicket(CheckOrderDto orderDto);

    ApiResult<?> finish(User currentUser, CheckOrderDto orderDto);

    ApiResult<?> delete(UUID id);
}
