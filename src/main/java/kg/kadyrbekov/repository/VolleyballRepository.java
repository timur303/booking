package kg.kadyrbekov.repository;

import kg.kadyrbekov.model.entity.Volleyball;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolleyballRepository extends JpaRepository<Volleyball, Long> {
}
