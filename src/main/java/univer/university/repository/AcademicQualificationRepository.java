package univer.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univer.university.entity.AcademicQualification;

@Repository
public interface AcademicQualificationRepository extends JpaRepository<AcademicQualification, Long> {

    @Query(value = """
    select * from academic_qualification where user_id = ?1
    """, nativeQuery = true)
    Page<AcademicQualification> findAllByUserId(Long userId, Pageable pageable);
}
