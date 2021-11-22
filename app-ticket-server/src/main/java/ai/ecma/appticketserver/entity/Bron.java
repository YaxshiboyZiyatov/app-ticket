package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import ai.ecma.appticketserver.enums.BronStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE bron SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class Bron extends AbsEntity {

    @OneToOne
    private Ticket ticket;

    @ManyToOne
    private User user;

    @Column(name = "bron_time")
    private Timestamp bronTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "bron_status_enum")
    private BronStatusEnum bronStatusEnum;

    @Column(name = "end_time")
    private Timestamp endTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private TicketPayment ticketPayment;

}

