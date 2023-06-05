package kg.kadyrbekov.repository;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    List<OrderHistory> findByUser(User user);

}
