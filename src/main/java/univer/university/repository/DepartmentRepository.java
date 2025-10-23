package univer.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import univer.university.entity.Department;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByCollegeIdAndActiveTrue(Long collegeId);
    Optional<Department> findByIdAndActiveTrue(Long id);


    @Query(value = """
        select d.* from department d where
        (:name IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%',:name,'%'))) and d.active = true and
        (:collegeId IS NULL OR d.college_id = :collegeId) order by created_at desc
    """, nativeQuery = true)
    Page<Department> searchDepartment(@Param("name") String name,
                                      @Param("collegeId") Long collegeId, Pageable pageable);
}
