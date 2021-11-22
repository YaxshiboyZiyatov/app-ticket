package ai.ecma.appticketserver.service;

import ai.ecma.appticketserver.payload.ApiResult;
import ai.ecma.appticketserver.payload.CustomPage;
import ai.ecma.appticketserver.payload.TicketHistoryResDto;

import java.util.UUID;

public interface TicketHistoryService {

    ApiResult<CustomPage<TicketHistoryResDto>> getTicketHistory( UUID userId, int page, int size);

    // for admins
    ApiResult<CustomPage<TicketHistoryResDto>> getAllTicketHis(int page, int size);
}
