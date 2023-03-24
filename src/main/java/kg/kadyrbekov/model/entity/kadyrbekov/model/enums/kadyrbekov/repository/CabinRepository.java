package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.repository;

import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.Cabin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabinRepository extends JpaRepository<Cabin,Long> {
}
