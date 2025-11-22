package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univer.university.dto.response.ResLavozimStatistics;
import univer.university.entity.Lavozm;

import java.util.List;
import java.util.Map;

@Repository
public interface LavozimRepository extends JpaRepository<Lavozm, Long> {

    boolean existsByName(String name);

    @Query(
            value = """
                    SELECT l.name AS name,
                           COUNT(u.id) AS count
                    FROM lavozm l
                    LEFT JOIN users u ON u.lavozm_id = l.id
                    GROUP BY l.name
                    ORDER BY count DESC
                    """,
            nativeQuery = true
    )
    List<Object[]> getLavozimStats();



}
