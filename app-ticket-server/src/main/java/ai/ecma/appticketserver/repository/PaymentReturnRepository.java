package ai.ecma.appticketserver.repository;


import ai.ecma.appticketserver.entity.PaymentReturn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentReturnRepository extends JpaRepository<PaymentReturn, UUID> {
    Optional<PaymentReturn> findByTicketId(UUID ticket_id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE payment_return set success_return=true where order_id=:orderId", nativeQuery = true)
    void completePaymentReturnByOrder(@Param("orderId") UUID orderId);

    List<PaymentReturn> findAllByTicketIdInAndSuccessReturnFalse(Collection<UUID> ticket_id);


    @Query(value = "SELECT pr.* from payment_return pr " +
            "join ticket t on pr.ticket_id=t.id " +
            " join event_session es on t.event_session_id=es.id " +
            " where event_id=:eventId ",
            nativeQuery = true)
    Page<PaymentReturn> findAllByEventHistory(@Param("eventId") UUID eventId, Pageable pageable);

    @Query(value = "SELECT sum(pr.amount) from payment_return pr " +
            "join ticket t on pr.ticket_id=t.id " +
            " join event_session es on t.event_session_id=es.id " +
            " where event_id=:eventId ",
            nativeQuery = true)
    double getMaxEventSum(@Param("eventId") UUID eventId);

    List<PaymentReturn> findAllByCreatedAtBetweenAndSuccessReturnTrue(Timestamp createdAt, Timestamp createdAt2);
}
