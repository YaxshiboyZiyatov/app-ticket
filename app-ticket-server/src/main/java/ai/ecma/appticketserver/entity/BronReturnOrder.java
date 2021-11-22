package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
public class BronReturnOrder extends AbsEntity {


    @Column(nullable = false,columnDefinition = "text[]")
    @Type(type = "string-array")
    private String[] bronIdList;

    @Column(nullable = false)
    private String cardNumber;

    @Column(nullable = false)
    private double price;



}
