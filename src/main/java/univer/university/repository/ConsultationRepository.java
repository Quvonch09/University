package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.Consultation;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

}
