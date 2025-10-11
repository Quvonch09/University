package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import univer.university.entity.Info;

import java.util.List;

@Repository
public interface InfoRepository extends JpaRepository<Info, Long> {

    @Query(value = """
        SELECT i.*
        FROM information i
        JOIN category c ON c.id = i.category_id
        JOIN user_info ui ON ui.id = c.user_info_id
        JOIN users u ON u.id = ui.user_id
        WHERE u.id = :userId
    """, nativeQuery = true)
    List<Info> getInfosByUserId(@Param("userId") Long userId);

}
