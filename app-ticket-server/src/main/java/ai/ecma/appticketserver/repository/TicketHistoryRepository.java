package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.TicketHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketHistoryRepository extends JpaRepository<TicketHistory, UUID> {


}
