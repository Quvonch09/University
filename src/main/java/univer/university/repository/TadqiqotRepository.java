package univer.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univer.university.entity.Consultation;
import univer.university.entity.Tadqiqot;

@Repository
public interface TadqiqotRepository extends JpaRepository<Tadqiqot, Long> {

    Page<Tadqiqot> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query(value = """
    select * from tadqiqot where user_id = ?1 order by created_at desc
    """, nativeQuery = true)
    Page<Tadqiqot> findAllByUser(Long userId, Pageable pageable);


    long countAllByUserId(Long userId);
}
