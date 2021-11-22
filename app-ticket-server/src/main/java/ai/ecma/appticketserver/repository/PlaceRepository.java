package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.UUID;

public interface PlaceRepository extends JpaRepository <Place, UUID> {

  boolean existsByAddressAndIdNot(String address, UUID id);

}
