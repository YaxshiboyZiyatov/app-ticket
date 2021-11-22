package ai.ecma.appticketserver.service;


import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.payload.*;

import java.util.UUID;

public interface PaymentReturnService {

    ApiResult<CustomPage<PaymentReturnResDto>> getAll(int page, int size);

    ApiResult<PaymentReturnResDto> get(UUID id);

    ApiResult<CustomPage<PaymentReturnResDto>> returnPaymentByEventSum(UUID eventId, int page, int size);

    ApiResult<?> cancelBron(BronReturnOrderDto bronReturnOrderDto);

    ApiResult<?> ticketPaymentReturn(TicketReturnOrderDto ticketReturnOrderDto);

   ApiResult<?> cancelTickets(TicketCancelDto ticketCancelDto, User user);

    ApiResult<PaymentReportResDto> reportCancel(DateReqDto dateReqDto);
}
