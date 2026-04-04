package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.ActionEvent;

@Repository
public interface ActionEventRepository extends JpaRepository<ActionEvent, Long> {
}
