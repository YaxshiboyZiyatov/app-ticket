package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.BronReturnOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BronReturnOrderRepository extends JpaRepository<BronReturnOrder, UUID> {
}
