package ai.ecma.appticketserver.utils;

import ai.ecma.appticketserver.entity.*;
import ai.ecma.appticketserver.payload.*;

import java.util.Objects;

public class Mapper {

    public static UserResDto userResDto(User user) {
        return new UserResDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                roleResDto(user.getRole())
        );
    }

    public static RoleResDto roleResDto(Role role) {
        return new RoleResDto(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.getRoleType(),
                role.getPermission()
        );
    }

    public static OrderResDto orderResDto(Order order) {
        return new OrderResDto(
                order.getId(),
                userResDto(order.getUser()),
                order.getPrice(),
                order.getFinished()
        );
    }

    public static TicketResDto ticketResDto(Ticket ticket) {
        return new TicketResDto(
                ticket.getId(),
                ticket.getSeatName(),
                !Objects.isNull(ticket.getUser()) ? ticket.getUser().getId() : null,
                !Objects.isNull(ticket.getEventSession()) ? ticket.getEventSession().getId() : null,
                ticket.getStatusEnum().name(),
                ticket.getRow(),
                ticket.getSection(),
                ticket.getPrice()
        );

    }


    public static PayTypeResDto payTypeResDto(PayType payType) {
        return new PayTypeResDto(
                payType.getId(),
                payType.getName(),
                payType.getPayTypeEnum()
        );
    }


    public static EventSessionResDto eventSessionResDtoTo(EventSession eventSession) {
        return new EventSessionResDto(
                eventSession.getId(),
                eventResDtoTo(eventSession.getEvent()),
                eventSession.getStartTime(), eventSession.getEndTime(),
                !Objects.isNull(eventSession.getBronTariff())
                        ? bronTariffResDtoTo(eventSession.getBronTariff())
                        : null
        );
    }

    public static EventResDto eventResDtoTo(Event event) {
        return new EventResDto(
                event.getId(),
                event.getName(),
                !Objects.isNull(event.getBanner())
                        ? CommonUtils.urlBuilder(event.getBanner().getId())
                        : null,
                !Objects.isNull(event.getSchema())
                        ? CommonUtils.urlBuilder(event.getSchema().getId())
                        : null,
                placeResToDto(event.getPlace()),
                event.getDescription()
        );
    }


    public static PlaceResDto placeResToDto(Place place) {
        return new PlaceResDto(
                place.getId(),
                place.getAddress(),
                place.getLat(),
                place.getLon()
        );
    }

    public static BronTariffResDto bronTariffResDtoTo(BronTariff bronTariff) {
        return new BronTariffResDto(
                bronTariff.getId(),
                bronTariff.getLifetime(),
                bronTariff.getPercent(),
                bronTariff.isDisabled()
        );
    }

    public static PaymentReturnResDto paymentReturnResDto(PaymentReturn paymentReturn) {
        return new PaymentReturnResDto(
                paymentReturn.getId(),
                paymentReturn.getAmount(),
                paymentReturn.getTicket(),
                paymentReturn.getCardNumber(),
                paymentReturn.isSuccessReturn()

        );
    }


}
