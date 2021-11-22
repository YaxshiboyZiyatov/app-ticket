package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.BronTariff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BronTariffRepository extends JpaRepository<BronTariff, UUID> {


}
