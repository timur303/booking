package kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.repository;

import kg.kadyrbekov.model.entity.kadyrbekov.model.enums.kadyrbekov.model.entity.kadyrbekov.model.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club,Long> {
}
