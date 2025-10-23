package univer.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import univer.university.entity.College;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollageRepository extends JpaRepository<College, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Long id);

    @Query(value = """
        select * from college where
        (:name IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%',:name,'%'))) and
        active = true order by created_at desc
    """, nativeQuery = true)
    List<College> searchAllByNameLikeAndActiveTrue(@Param("name") String name);

    Optional<College> findByIdAndActiveTrue(Long id);

    @Query(value = """
        select * from college where
        (:name IS NULL OR LOWER(name) LIKE LOWER(CONCAT('%',:name,'%'))) and
        active = true order by created_at desc
    """, nativeQuery = true)
    Page<College> findByCollegeByPage(@Param("name") String name, Pageable pageable);
}
