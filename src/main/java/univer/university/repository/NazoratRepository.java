package univer.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univer.university.entity.Consultation;
import univer.university.entity.Nazorat;

@Repository
public interface NazoratRepository extends JpaRepository<Nazorat, Long> {

    Page<Nazorat> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query(value = """
    select * from nazorat where user_id = ?1 order by created_at desc
    """, nativeQuery = true)
    Page<Nazorat> findAllByUser(Long userId, Pageable pageable);

    long countAllByUserId(Long userId);
}
