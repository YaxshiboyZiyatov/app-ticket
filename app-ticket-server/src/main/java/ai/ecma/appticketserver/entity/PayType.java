package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import ai.ecma.appticketserver.enums.PayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE pay_type SET deleted=true where id=?")
@Where(clause = "deleted=false")
public class PayType extends AbsEntity {

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private PayTypeEnum payTypeEnum;
}
