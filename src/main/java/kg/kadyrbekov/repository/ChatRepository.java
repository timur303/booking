package kg.kadyrbekov.repository;

import kg.kadyrbekov.model.User;
import kg.kadyrbekov.model.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    Chat findByUser1AndUser2(Optional<User> user1, Optional<User> user2);

}
