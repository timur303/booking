package kg.kadyrbekov.repository;

import kg.kadyrbekov.entity.Computer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComputerRepository extends JpaRepository<Computer, Long> {
}
