package ai.ecma.appticketserver.utils;

public interface AppConstant {

    String BASE_PATH = "/api";
    String DOMAIN = "http://localhost";


    /*====CONTROLLERS=====*/
    String PAY_TYPE_CONTROLLER = BASE_PATH + "/pay-type";
    String PLACE_CONTROLLER = BASE_PATH + "/place";
    String BRON_TARIFF_CONTROLLER = BASE_PATH + "/bron-tariff";
    String AUTH_CONTROLLER = BASE_PATH + "/auth";
    String ROLE_CONTROLLER = BASE_PATH + "/role";
    String ARTIST_CONTROLLER = BASE_PATH + "/artist";
    String ATTACHMENT_CONTROLLER = BASE_PATH + "/attachment";
    String EVENT_CONTROLLER = BASE_PATH + "/event";
    String EVENT_SESSION_CONTROLLER = BASE_PATH + "/event-session";
    String SEAT_TEMPLATE = BASE_PATH + "/seatTemplate";
    String SEAT_TEMPLATE_CHAIR = BASE_PATH + "/seatTemplateChair";
    String BRON_CONTROLLER = BASE_PATH + "/bron";
    String PAYMENT_CONTROLLER = BASE_PATH + "/payment";
    String TICKET_CONTROLLER = BASE_PATH + "/ticket";
    String ORDER_CONTROLLER = BASE_PATH + "/order";
    String EVENT_ARTIST_CONTROLLER = "/event-artist";
    String PAYMENT_RETURN_CONTROLLER = "/payment-return";
    String USER_CONTROLLER = BASE_PATH + "/user";


    String DEFAULT_PAGE_NUMBER = "0";
    String DEFAULT_PAGE_SIZE = "20";
}
