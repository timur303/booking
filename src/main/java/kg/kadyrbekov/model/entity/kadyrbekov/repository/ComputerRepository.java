package kg.kadyrbekov.model.entity.kadyrbekov.repository;

import kg.kadyrbekov.model.entity.kadyrbekov.model.entity.Computer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, Long> {
}
