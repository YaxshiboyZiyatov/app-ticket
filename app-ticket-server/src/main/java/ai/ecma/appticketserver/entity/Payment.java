package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@SQLDelete(sql = "UPDATE payment SET deleted=true where=?")
@Where(clause = "deleted=false")
public class Payment extends AbsEntity {

    private double amount;

    @ManyToOne
    private PayType payType;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "payment",cascade = CascadeType.ALL)
    private List<TicketPayment> ticketPaymentList;

    public Payment(double amount, PayType payType, User user) {
        this.amount = amount;
        this.payType = payType;
        this.user = user;
    }
}
