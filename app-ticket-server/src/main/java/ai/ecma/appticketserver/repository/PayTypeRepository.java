package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.PayType;
import ai.ecma.appticketserver.enums.PayTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PayTypeRepository extends JpaRepository<PayType, UUID> {
    Optional<PayType> findByNameAndPayTypeEnum(String name, PayTypeEnum payTypeEnum);
}
