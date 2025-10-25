package univer.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.Award;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {

    Page<Award> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
