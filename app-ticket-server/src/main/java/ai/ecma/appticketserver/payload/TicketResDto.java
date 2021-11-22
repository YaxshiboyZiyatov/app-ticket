package ai.ecma.appticketserver.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResDto {

    private UUID id;

    private String seatName;

    private UUID userId;

    private UUID eventSessionId;

    private String statusEnum;

    private int row;

    private String section;

    private Double price;





}
