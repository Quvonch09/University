package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.Publication;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

}
