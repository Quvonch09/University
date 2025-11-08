package univer.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univer.university.entity.Award;
import univer.university.entity.enums.AwardEnum;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {

    Page<Award> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query(value = """
    select a.* from award a where a.user_id = ?1 order by created_at desc
    """, nativeQuery = true)
    Page<Award> findByUser(Long userId, Pageable pageable);


    long countAllByUserIdAndAwardEnum(Long userId, AwardEnum awardEnum);
    long countAllByUserId(Long userId);
}
