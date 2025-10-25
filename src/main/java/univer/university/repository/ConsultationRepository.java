package univer.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univer.university.entity.Consultation;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    @Query(value = """
    select * from consultation where user_id = ?1 order by created_at desc
    """, nativeQuery = true)
    Page<Consultation> findAllByUser(Long userId, Pageable pageable);

}
