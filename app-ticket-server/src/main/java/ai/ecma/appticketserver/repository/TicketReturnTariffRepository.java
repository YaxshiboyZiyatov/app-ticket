package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.TicketReturnTariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TicketReturnTariffRepository extends JpaRepository<TicketReturnTariff, UUID> {

    @Query(value = "SELECT trt.* from ticket t join event_session es on t.event_session_id =es.id " +
            "join event e on es.event_id=e.id " +
            "join ticket_return_tariff trt on trt.event_id= e.id where t.id=:ticketId and period_time<:period " +
            "order by period_time desc limit 1 ", nativeQuery = true)
    Optional<TicketReturnTariff> getTicketReturnTariffId(@Param("ticketId") UUID ticketId, @Param("period") int period);

}
