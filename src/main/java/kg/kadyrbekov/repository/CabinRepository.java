package kg.kadyrbekov.repository;

import kg.kadyrbekov.model.entity.Cabin;
import kg.kadyrbekov.model.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CabinRepository extends JpaRepository<Cabin,Long> {

    boolean existsByClubAndName(Club club, String name);

}
