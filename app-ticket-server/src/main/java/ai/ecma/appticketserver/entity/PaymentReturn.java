package ai.ecma.appticketserver.entity;

import ai.ecma.appticketserver.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class PaymentReturn extends AbsEntity {

    private double amount;

    @ManyToOne
    private Ticket ticket;

    @Column(name = "card_name")
    private String cardNumber;

    @Column(name = "success_return")
    private boolean successReturn;


    private UUID orderId;



    public PaymentReturn(double amount, Ticket ticket, String cardNumber, boolean successReturn) {
        this.amount = amount;
        this.ticket = ticket;
        this.cardNumber = cardNumber;
        this.successReturn = successReturn;
    }
}
