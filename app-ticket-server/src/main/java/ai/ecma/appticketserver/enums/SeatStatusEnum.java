package ai.ecma.appticketserver.enums;

public enum SeatStatusEnum {
    VACANT("HOZIRCHA BO'SH"),
    BOOKED("Pul to'lab bron qilindi"),
    RESERVED("Admin zaxiraga olib qo'ygan"),
    SOLD("Sotilgan"),
    VIP("Admin zaxiraga olib qo'ygan"),
    PAYMENT_PROCESS("To'lov jarayonida");
    public String nameUZ;

    SeatStatusEnum(String nameUZ) {
        this.nameUZ = nameUZ;
    }
}
