package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class BronTariff extends AbsEntity {

    private int lifetime; //daqiqalarda

    private int percent; //narxning necha % qismi to'lanishi

    private boolean disabled;

}
