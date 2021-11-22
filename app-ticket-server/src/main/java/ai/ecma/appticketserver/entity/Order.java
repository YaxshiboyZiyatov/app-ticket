package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import ai.ecma.appticketserver.enums.OrderTypeEnum;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "orders")
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
public class Order extends AbsEntity {

    @ManyToOne
    private User user;

    @Column(columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] ticketsId;

    private double price;

    @Enumerated(EnumType.STRING)
    private OrderTypeEnum orderType;

    private Boolean finished = false;
}
