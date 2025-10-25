package univer.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.Nazorat;

@Repository
public interface NazoratRepository extends JpaRepository<Nazorat, Long> {

    Page<Nazorat> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
