package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@SQLDelete(sql = "UPDATE seat_template SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class SeatTemplate extends AbsEntity {

    private String name;
    @OneToOne
    private Attachment schema;

    @OneToMany(mappedBy = "seatTemplate", cascade = CascadeType.ALL)
    private List<SeatTemplateChair> seatTemplateChairs;

    public SeatTemplate(String name, Attachment schema) {
        this.name = name;
        this.schema = schema;
    }
}
