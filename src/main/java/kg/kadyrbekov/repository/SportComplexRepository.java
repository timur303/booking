package kg.kadyrbekov.repository;

import kg.kadyrbekov.model.entity.SportComplex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SportComplexRepository extends JpaRepository<SportComplex, Long> {

    Optional<SportComplex> findByComplexManagerId(Long id);
}
