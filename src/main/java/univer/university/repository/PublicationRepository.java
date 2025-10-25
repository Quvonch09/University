package univer.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univer.university.entity.Consultation;
import univer.university.entity.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {
    @Query(value = """
    select * from publication where user_id = ?1 order by created_at desc
    """, nativeQuery = true)
    Page<Publication> findAllByUser(Long userId, Pageable pageable);
}
