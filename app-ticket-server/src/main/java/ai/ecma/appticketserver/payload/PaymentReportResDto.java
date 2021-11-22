package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReportResDto {

    private double payAmount;

    private double cancelAmount;

//    bizda qolgan realniy pul miqdori
    private double balance;

//    private UUID eventId;  un yana bitta yol;   bron va sotib olishni ajratish;
}
