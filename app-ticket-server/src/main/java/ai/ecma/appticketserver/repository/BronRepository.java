package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.Bron;
import ai.ecma.appticketserver.entity.Ticket;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.BronStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.*;

public interface BronRepository extends JpaRepository<Bron, UUID> {
    List<Bron> findAllByEndTimeBeforeAndBronStatusEnumNot(Timestamp endTime, BronStatusEnum bronStatusEnum);

    List<Bron> findAllByIdInAndUserId(Set<UUID> id, UUID user_id);

    Page<Bron> findAllByBronStatusEnum(BronStatusEnum bronStatusEnum, Pageable pageable);

    Page<Bron> findAllByUserId(Pageable pageable, UUID user_id);
//    @Query(value="select * from bron where bron_status_enum =: bronStatus",nativeQuery = true)
//    List<Bron> findBronStatus(@Param("bronStatus") String bronStatus);

    List<Bron> findAllByTicketIdInAndUser(Collection<UUID> ticket_id, User user);

    List<Bron> findAllByTicketIdIn(Collection<UUID> ticket_id);

//    Optional<Bron> findFirstByTicketAndUserOrderByCreatedAtDesc(Ticket ticket, User user);
    Optional<Bron> findFirstByTicketAndBronStatusEnumAndUserOrderByCreatedAtDesc(Ticket ticket, BronStatusEnum bronStatusEnum, User user);
}
