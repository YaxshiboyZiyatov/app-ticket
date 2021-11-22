package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {
    Optional<VerificationCode> findByPhoneNumberAndCodeAndUsedIsFalseAndCreatedAtAfter(String phoneNumber, String code, Timestamp createdAt);

    boolean existsByPhoneNumberAndCodeAndUsedIsTrue(String phoneNumber, String code);

    @Transactional
    @Modifying
    void deleteByPhoneNumberAndCodeAndUsedTrue(String phoneNumber, String code);

    @Query(value = "select count(id) from verification_code where created_at>:lastTime and phone_number=:phoneNumber and used=false",nativeQuery = true)
    long countPhoneNumbers(@Param("lastTime")Timestamp lastTime,@Param("phoneNumber") String phoneNumber);
}
