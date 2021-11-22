package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.EventSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventSessionRepository extends JpaRepository<EventSession, UUID> {
    @Query(value = "select * from event_session es join event e on  es.event_id = e.id " +
            " where " +
            " (:startTime between start_time and end_time)"+
            " or (:endTime between start_time and end_time) " +
            " or (start_time between :startTime and :endTime)"+
            " or (end_time between :startTime and :endTime)"+
            " and e.place_id = :placeId " +
            " and (es.deleted = false and e.deleted=false)", nativeQuery = true)
    List<EventSession> findConflictEventSessions(@Param("startTime") Timestamp startTime,
                                                 @Param("endTime") Timestamp endTime,
                                                 @Param("placeId") UUID placeId);

    @Query(value = "select * from event_session es join event e on  es.event_id = e.id" +
            " where " +
            " (:startTime between start_time and end_time)"+
            " or (:endTime between start_time and end_time) " +
            " or (start_time between :startTime and :endTime)"+
            " or (end_time between :startTime and :endTime)"+
            " and e.place_id = :placeId " +
            " and es.event_session_id<> :eventSessionId"+
            " and (es.deleted = false and e.deleted=false)", nativeQuery = true)
    List<EventSession> findConflictEventSessionsByID(@Param("startTime") Timestamp startTime,
                                                 @Param("endTime") Timestamp endTime,
                                                 @Param("placeId") UUID placeId,
                                                 @Param("eventSessionId") UUID eventSessionId);

    Page<EventSession> findByEventId(UUID event_id, Pageable pageable);

    @Query(value = "select * from event_session es join event e on  es.event_id = e.id" +
            " where " +
            " (:startTime between es.start_time and es.end_time)"+
            " or (:endTime between es.start_time and es.end_time) " +
            " or (es.start_time between :startTime and :endTime)"+
            " or (es.end_time between :startTime and :endTime)"+
            " and e.place_id = :placeId " +
            " and (es.deleted = false and e.deleted=false)", nativeQuery = true)
    Page<EventSession> getByIdAndTimes(@Param("startTime")Timestamp startTime,
                                       @Param("endTime") Timestamp endTime,
                                       @Param("placeId") UUID placeId,
                                       Pageable pageable);

}

