package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.PayBackInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PayBackInfoRepository extends JpaRepository<PayBackInfo, UUID> {
}
