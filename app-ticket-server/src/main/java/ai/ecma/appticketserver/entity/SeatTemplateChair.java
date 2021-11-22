package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import ai.ecma.appticketserver.enums.SeatStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE seat_template_chair SET deleted=true where id=?")
@Where(clause = "deleted=false")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"name_seat","row","section","seat_template_id"}))
public class SeatTemplateChair extends AbsEntity {   //                                        2

    @Enumerated(EnumType.STRING)
    @Column(name = "status_enum")
    private SeatStatusEnum seatStatusEnum;
    @Column(name = "name_seat")
    private String nameSeat;
    private double price;
    private int row;
    private String section;
    @ManyToOne(fetch = FetchType.LAZY)
    private SeatTemplate seatTemplate;

}
