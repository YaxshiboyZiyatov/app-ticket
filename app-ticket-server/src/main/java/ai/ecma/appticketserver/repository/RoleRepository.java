package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.Role;
import ai.ecma.appticketserver.enums.RoleTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByRoleType(RoleTypeEnum roleType);
    boolean existsByNameAndIdNot(String name, UUID id);
    boolean existsByName(String name);
}
