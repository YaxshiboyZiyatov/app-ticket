package ai.ecma.appticketserver.enums;

public enum PayTypeEnum {
    CASH("Naqd pul"),
    TRANSFER("Pul o'tkazmasi"),
    CARD("Karta orqali");


    public String nameUz;

    PayTypeEnum(String nameUz) {
        this.nameUz = nameUz;
    }
}
