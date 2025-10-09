package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.Info;

@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {
}
