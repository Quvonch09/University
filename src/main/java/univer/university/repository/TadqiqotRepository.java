package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.Tadqiqot;

@Repository
public interface TadqiqotRepository extends JpaRepository<Tadqiqot, Long> {
}
