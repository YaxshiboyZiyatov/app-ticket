package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.entity.Order;
import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.DateReqDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymeService {

    public ApiResult<?> returnPaymentBron(double price, String cardNumber, UUID bronReturnOrderId) {

        return ApiResult.successResponse();

    }

    public ApiResult<?> returnPaymentTicket(double price, String cardNumber, UUID ticketReturnOrderId) {

        return ApiResult.successResponse();

    }

    public ApiResult<?> ticketBookedBuy(double price, String cardNumber, UUID orderId) {

        return ApiResult.successResponse();

    }



}
