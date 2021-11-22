package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.SeatTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatTemplateRepository extends JpaRepository<SeatTemplate, UUID> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);

}
