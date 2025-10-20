package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.College;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollageRepository extends JpaRepository<College, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

    List<College> findAllByActiveTrue();

    Optional<College> findByIdAndActiveTrue(Long id);
}
