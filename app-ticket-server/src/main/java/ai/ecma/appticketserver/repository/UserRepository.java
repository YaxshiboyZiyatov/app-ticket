package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.User;
import ai.ecma.appticketserver.enums.RoleTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);

    Page<User> findAllByRole_RoleType(RoleTypeEnum role_roleType, Pageable pageable);

    @Query(value = "select * from users where  (LOWER(first_name)  like LOWER(:some)) " +
            " or (LOWER (last_name) like  LOWER (:some) ) " +
            "or (LOWER(phone_number) like LOWER (:some) )", nativeQuery = true)
    Page<User> getByFirstNameOrLastNameOrPhoneNumber(@Param("some") String some, Pageable pageable);

//    select * from product where UPPER(name) like UPPER(:txt) or " +
//            "UPPER(brand) like UPPER(:txt) or " +
//            "UPPER(tariff) like UPPER(:txt) or " +
//            "size=:inSize


}
