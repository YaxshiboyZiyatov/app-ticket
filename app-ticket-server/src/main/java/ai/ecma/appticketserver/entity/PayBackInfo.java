package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PayBackInfo extends AbsEntity {

    private double amount;

    @OneToOne
    private TicketPayment ticketPayment;

    @Column(name = "expense_amount")
    private double expenseAmount;

    @Column(name = "fine_amount")
    private double fineAmount;


}
