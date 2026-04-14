package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univer.university.entity.IlmiyDaraja;

import java.util.List;

@Repository
public interface IlmiyDarajaRepository extends JpaRepository<IlmiyDaraja, Long> {

    boolean existsByName(String name);

    @Query(
            value = """
                    SELECT d.name AS name,
                           COUNT(u.id) AS count
                    FROM ilmiy_daraja d
                             LEFT JOIN users u ON u.ilmiy_daraja_id = d.id WHERE u.enabled = true
                    GROUP BY d.name
                    ORDER BY count DESC
                    """,
            nativeQuery = true
    )
    List<Object[]> getIlmiyDarajaStats();


    @Query(value = """
    SELECT i.name, COUNT(u.id)
    FROM ilmiy_daraja i
    LEFT JOIN users u ON u.ilmiy_daraja_id = i.id
    GROUP BY i.id, i.name
""", nativeQuery = true)
    List<Object[]> getIlmiyDarajaStats2();
}