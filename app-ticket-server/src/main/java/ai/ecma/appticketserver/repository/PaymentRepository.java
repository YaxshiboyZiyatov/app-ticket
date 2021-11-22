package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.Payment;
import ai.ecma.appticketserver.entity.TicketPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {


    List<Payment> findAllByCreatedAtBetween(Timestamp createdAt, Timestamp createdAt2);

//    @Query(value = "select * from payment p join" +
//            " ticket_payment tp on tp.payment_id=p.id" +
//            " where tp.ticket_id in :ticketsId ", nativeQuery = true)
//    Optional<Payment> getPaymentByTickedIds(@Param("ticketsId") List<UUID> tickedsId);
}
