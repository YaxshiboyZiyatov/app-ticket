package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.SeatTemplateChair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatTemplateChairRepository extends JpaRepository<SeatTemplateChair, UUID> {
    boolean existsByNameSeatAndRowAndSectionAndSeatTemplateId(String nameSeat, int row, String section, UUID seatTemplate_id);
    boolean existsByNameSeatAndRowAndSectionAndSeatTemplateIdAndIdNot(String nameSeat, int row, String section, UUID seatTemplate_id, UUID id);
}
