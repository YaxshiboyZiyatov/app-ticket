package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import ai.ecma.appticketserver.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class TicketHistory extends AbsEntity {

    @ManyToOne
    private Ticket ticket;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_enum")
    private SeatStatusEnum seatStatusEnum;

}
