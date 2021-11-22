package ai.ecma.appticketserver.repository;

import ai.ecma.appticketserver.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
//    @Query(value = "",nativeQuery = true)
//    List<Order> getByOldOrders();
    List<Order> findAllByCreatedAtBeforeAndFinishedFalse(Timestamp createdAt);
}
