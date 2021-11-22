package ai.ecma.appticketserver.enums;

import lombok.Getter;

@Getter
public enum PermissionEnum {

    VIEW_ROLE(RoleTypeEnum.STAFF, "Rolni ko'rish"),
    ADD_ROLE(RoleTypeEnum.STAFF, "Rol qo'shish"),
    EDIT_ROLE(RoleTypeEnum.STAFF, "Rolni tahrirlash"),
    DELETE_ROLE(RoleTypeEnum.STAFF, "Rolni o'chirish"),

    VIEW_CLIENT(RoleTypeEnum.STAFF, ""),
    SEARCH_CLIENT(RoleTypeEnum.STAFF, ""),
    FILTER_CLIENT(RoleTypeEnum.STAFF, ""),
    EDIT_CLIENT(RoleTypeEnum.STAFF, ""),
    ADD_STAFF(RoleTypeEnum.STAFF, ""),
    EDIT_STAFF(RoleTypeEnum.STAFF, ""),
    DELETE_STAFF(RoleTypeEnum.STAFF, ""),

    ADD_EVENT(RoleTypeEnum.STAFF, ""),
    EDIT_EVENT(RoleTypeEnum.STAFF, ""),
    DELETE_EVENT(RoleTypeEnum.STAFF, ""),

    ADD_TICKET_RETURN_TARIFF(RoleTypeEnum.STAFF, ""),
    EDIT_TICKET_RETURN_TARIFF(RoleTypeEnum.STAFF, ""),
    DELETE_TICKET_RETURN_TARIFF(RoleTypeEnum.STAFF, ""),

    ADD_BRON_TARIFF(RoleTypeEnum.STAFF, ""),
    EDIT_BRON_TARIFF(RoleTypeEnum.STAFF, ""),
    DELETE_BRON_TARIFF(RoleTypeEnum.STAFF, ""),

    VIEW_TICKET_HISTORY(RoleTypeEnum.STAFF, ""),

    VIEW_PAYMENT(RoleTypeEnum.CLIENT, ""),
    EDIT_PAYMENT(RoleTypeEnum.STAFF, ""),
    DELETE_PAYMENT(RoleTypeEnum.STAFF, ""),

    ADD_PAY_TYPE(RoleTypeEnum.STAFF, ""),
    EDIT_PAY_TYPE(RoleTypeEnum.STAFF, ""),
    DELETE_PAY_TYPE(RoleTypeEnum.STAFF, ""),

    VIEW_PAYMENT_RETURN(RoleTypeEnum.STAFF, ""),
    VIEW_PAYBACK_INFO(RoleTypeEnum.STAFF, ""),
    VIEW_TICKET_PAYMENT(RoleTypeEnum.STAFF, ""),

    VIEW_BRON(RoleTypeEnum.CLIENT, ""),
    ADD_BRON(RoleTypeEnum.STAFF, ""),
    CANCEL_BRON(RoleTypeEnum.STAFF, ""),
    EDIT_BRON(RoleTypeEnum.STAFF, ""),
    DELETE_BRON(RoleTypeEnum.STAFF, ""),

    ADD_PLACE(RoleTypeEnum.STAFF, ""),
    EDIT_PLACE(RoleTypeEnum.STAFF, ""),
    DELETE_PLACE(RoleTypeEnum.STAFF, ""),

    VIEW_SEAT_TEMPLATE(RoleTypeEnum.STAFF, ""),
    ADD_SEAT_TEMPLATE(RoleTypeEnum.STAFF, ""),
    EDIT_SEAT_TEMPLATE(RoleTypeEnum.STAFF, ""),
    DELETE_SEAT_TEMPLATE(RoleTypeEnum.STAFF, ""),

    VIEW_SEAT_TEMPLATE_CHAIR(RoleTypeEnum.STAFF, ""),
    ADD_SEAT_TEMPLATE_CHAIR(RoleTypeEnum.STAFF, ""),
    EDIT_SEAT_TEMPLATE_CHAIR(RoleTypeEnum.STAFF, ""),
    DELETE_SEAT_TEMPLATE_CHAIR(RoleTypeEnum.STAFF, ""),

    ADD_TICKET(RoleTypeEnum.STAFF, ""),
    EDIT_TICKET(RoleTypeEnum.STAFF, ""),
    DELETE_TICKET(RoleTypeEnum.STAFF, ""),
    CANCEL_TICKET(RoleTypeEnum.STAFF, ""),

    VIEW_ARTIST(RoleTypeEnum.STAFF, ""),
    ADD_ARTIST(RoleTypeEnum.STAFF, ""),
    EDIT_ARTIST(RoleTypeEnum.STAFF, ""),
    DELETE_ARTIST(RoleTypeEnum.STAFF, ""),

    VIEW_ATTACHMENT(RoleTypeEnum.STAFF, ""),
    ADD_ATTACHMENT(RoleTypeEnum.STAFF, ""),
    EDIT_ATTACHMENT(RoleTypeEnum.STAFF, ""),
    DELETE_ATTACHMENT(RoleTypeEnum.STAFF, ""),

    VIEW_STAFF(RoleTypeEnum.STAFF, "");

    private final String nameUz;
    private final RoleTypeEnum roleType;

    PermissionEnum(RoleTypeEnum roleType, String roleDescription) {
        this.nameUz = roleDescription;
        this.roleType = roleType;
    }
}
