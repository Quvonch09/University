package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.AcademicQualification;

@Repository
public interface AcademicQualificationRepository extends JpaRepository<AcademicQualification, Long> {
}
