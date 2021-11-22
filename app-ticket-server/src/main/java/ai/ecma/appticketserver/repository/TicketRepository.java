package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.Ticket;
import ai.ecma.appticketserver.entity.TicketReturnTariff;
import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.SeatStatusEnum;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findAllByUserId(UUID userId);

    @Transactional
    @Modifying
    @Query(value = "update ticket set status_enum = :status where id in :ticketsId", nativeQuery = true)
    void changeVacantOldTickets(@Param("ticketsId") List<UUID> ticketsId, @Param("status") String seatStatus);

    List<Ticket> findAllByIdInAndUser(Collection<UUID> id, User user);

    Page<Ticket> findAllByEventSessionIdAndStatusEnum(UUID eventSession_id, SeatStatusEnum statusEnum, Pageable pageable);

    List<Ticket> findAllByUserIdAndIdIn(UUID user_id, Collection<UUID> id);

    Page<Ticket> findAllByStatusEnum(SeatStatusEnum statusEnum, Pageable pageable);

}
