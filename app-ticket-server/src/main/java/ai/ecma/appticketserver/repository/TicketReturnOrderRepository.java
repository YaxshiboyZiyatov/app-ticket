package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.TicketReturnOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketReturnOrderRepository extends JpaRepository<TicketReturnOrder, UUID> {
}
