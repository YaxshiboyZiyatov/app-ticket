package ai.ecma.appticketserver.controller;

import ai.ecma.appticketserver.aop.CurrentUser;
import ai.ecma.appticketserver.entity.Bron;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.BronStatusEnum;
import ai.ecma.appticketserver.payload.*;
import ai.ecma.appticketserver.utils.AppConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;
import java.util.UUID;

@RequestMapping(AppConstant.BRON_CONTROLLER)
@Tag(name = "Bron controller", description = "Bu bron controller")
public interface BronController {

    @GetMapping("/filter-by-status")
    ApiResult<CustomPage<BronResDto>> getStatus(@RequestParam int page, @RequestParam int size, @RequestBody BronStatusEnum bronStatus);

    @GetMapping("/filter-by-user")
    ApiResult<CustomPage<BronResDto>> getByUserId(@RequestParam int page, @RequestParam int size, @CurrentUser User user);

    @GetMapping
    ApiResult<CustomPage<BronResDto>> getAll(@RequestParam int page, @RequestParam int size);

    @GetMapping("/{id}")
    ApiResult<BronResDto> getById(@PathVariable UUID id);

    @PostMapping
    ApiResult<BronResDto> add(@CurrentUser User user, @RequestBody @Valid BronReqDto bronReqDto);

    @PutMapping("/cancel-bron")
    ApiResult<?> cancelBron(@CurrentUser User user, @RequestBody BronCancelDto bronCancelDto);

    @PostMapping("/booked-buy")
    ApiResult<?> bookedTicketBuy(@CurrentUser User user, @RequestBody TicketBuyDto ticketBuyDto);

    @PutMapping("/{id}")
    ApiResult<BronResDto> edit(@PathVariable UUID id, @RequestBody @Valid BronReqDto bronReqDto);

    @DeleteMapping("/{id}")
    ApiResult<?> delete(@PathVariable UUID id);

    @PostMapping("/booked-buy/finish")
    ApiResult<?> ticketBuy(@CurrentUser User user, @RequestBody OrderPayDto orderPayDto);

}
