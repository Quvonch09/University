package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univer.university.entity.Lavozm;

import java.util.List;
import java.util.Map;

@Repository
public interface LavozimRepository extends JpaRepository<Lavozm, Long> {

    boolean existsByName(String name);

//    @Query("SELECT l.name AS name, COUNT(l.id) AS count FROM lavozm l GROUP BY l.name")
//    List<Map<String, Object>> getLavozmStats();


}
