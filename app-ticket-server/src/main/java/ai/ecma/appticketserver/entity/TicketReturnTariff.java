package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TicketReturnTariff extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @Column(name = "period_time")
    private int periodTime;//Minutda

    @Column(name = "return_percent")
    private double returnPercent;

}
