package ai.ecma.appticketserver.enums;

public enum BronStatusEnum {

    ACTIVE("AKTIV"),
    CANCELED("BEKOR QILINDI"),
    AUTO_CANCELED("AUTO BEKOR QILINDI"),
    COMPLETED("TUGALLANDI");

    public String nameUz;

    BronStatusEnum(String nameUz) {
        this.nameUz = nameUz;
    }
}
