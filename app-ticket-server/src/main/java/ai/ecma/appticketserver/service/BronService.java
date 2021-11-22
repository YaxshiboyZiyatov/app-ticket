package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.BronStatusEnum;
import ai.ecma.appticketserver.payload.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

public interface BronService {


    ApiResult<CustomPage<BronResDto>> getStatus(int page, int size, BronStatusEnum bronStatus);

    ApiResult<CustomPage<BronResDto>> getByUserId(int page, int size, User user);

    ApiResult<CustomPage<BronResDto>> getAll(int page, int size);

    ApiResult<BronResDto> getById(UUID id);

    ApiResult<BronResDto> add(User user, BronReqDto bronReqDto);

    ApiResult<?> cancelBron(User user, BronCancelDto bronCancelDto);

    ApiResult<BronResDto> edit(UUID id, BronReqDto bronReqDto);

    ApiResult<?> delete(UUID id);


    ApiResult<?> bookedTicketBuy(User user, TicketBuyDto ticketBuyDto);

    ApiResult<?> ticketBuy(User user, OrderPayDto orderPayDto);
}
