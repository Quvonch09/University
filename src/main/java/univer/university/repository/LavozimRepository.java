package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.Lavozm;

@Repository
public interface LavozimRepository extends JpaRepository<Lavozm, Long> {
    boolean existsByName(String name);
}
