package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.Payment;
import ai.ecma.appticketserver.entity.Ticket;
import ai.ecma.appticketserver.entity.TicketPayment;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.OrderTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketPaymentRepository extends JpaRepository<TicketPayment, UUID> {

    Optional<TicketPayment> findByIdNotAndTicketAndPayment_User(UUID id, Ticket ticket, User payment_user);

//    Optional<TicketPayment> findFirstByTicketAndPayment_UserAndOrderTypeEnumOrderByCreatedAt(Ticket ticket, User payment_user, OrderTypeEnum orderTypeEnum);
    Optional<TicketPayment> findFirstByTicketAndPayment_UserAndReturnedFalseAndOrderTypeEnumOrderByCreatedAtDesc(Ticket ticket, User payment_user, OrderTypeEnum orderTypeEnum);

    Optional<TicketPayment> findByTicketAndPaymentAndPayment_UserAndOrderTypeEnum(Ticket ticket, Payment payment, User payment_user, OrderTypeEnum orderTypeEnum);

}
