package univer.university.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import univer.university.dto.response.AgeGenderStatsProjection;
import univer.university.dto.response.GenderStatsProjection;
import univer.university.entity.User;
import univer.university.entity.enums.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);

    boolean existsByPhoneAndRole(String phone, Role role);


    @Query(value = """
    select count(u.*) from users u join department d on u.department_id = d.id
        join college c on d.college_id = c.id where c.id = ?1 and c.active = true
    """, nativeQuery = true)
    long countByCollege(Long collegeId);


    @Query(value = """
    select count(u.*) from users u join department d on u.department_id = d.id where d.id = ?1 and d.active = true
    """, nativeQuery = true)
    long countByDepartment(Long departmentId);


    @Query(value = """
    select count(u.*) from users u join department d on u.department_id = d.id 
        join college c on d.college_id = c.id join user_info uf on u.id = uf.user_id 
        where c.id = ?1 and uf.academic_title = ?2 and c.active = true
    """, nativeQuery = true)
    long countAcademicByCollege(Long collegeId, String academicTitle);


    @Query(value = """
    select count(u.*) from users u join user_info uf on u.id = uf.user_id join department d on u.department_id = d.id
     where d.department_id = ?1 and uf.academic_title = ?2 and d.active = true
    """, nativeQuery = true)
    long countAcademicByDepartment(Long departmentId, String academicTitle);


    @Query(value = """
    select count(u.*) from users u join department d on u.department_id = d.id join college c on d.college_id = c.id
    join user_info uf on u.id = uf.user_id where c.id = ?1 and uf.level = ?2 and c.active = true
    """, nativeQuery = true)
    long countLevelByCollege(Long collegeId, String level);


    @Query(value = """
    select count(u.*) from users u join user_info uf on u.id = uf.user_id join department d on d.id = u.department_id
                      where d.department_id = ?1 and uf.level = ?2 and d.active = true
    """, nativeQuery = true)
    long countLevelByDepartment(Long departmentId, String level);


    @Query(value = """
    select count(u.*) from users u join department d on u.department_id = d.id join college c on d.college_id = c.id
    join user_info uf on u.id = uf.user_id where c.id = ?1 and uf.level IS NULL and uf.academic_title IS NULL
    """, nativeQuery = true)
    long countByCollegeNone(Long collegeId);


    @Query(value = """
    select count(u.*) from users u join user_info uf on u.id = uf.user_id 
        where u.department_id = ?1 and uf.level IS NULL and uf.academic_title IS NULL
    """, nativeQuery = true)
    long countByDepartmentNone(Long departmentId);


    long countByRole(Role role);

    @Query(value = """
    select count(u.*) from users u where u.gender = ?1 AND u.role <> 'ROLE_ADMIN';
    """, nativeQuery = true)
    long countByGender(boolean gender);

    @Query(value = """
    select count(u.*) from users u join academic_qualification aq on u.id = aq.user_id
                      where aq.degree_level IS NOT NULL and aq.specialization IS NOT NULL
    """, nativeQuery = true)
    long countByAcademic();

    @Query(value = """
    SELECT
        COUNT(*) AS total,
        SUM(CASE WHEN gender = true THEN 1 ELSE 0 END) AS maleCount,
        SUM(CASE WHEN gender = false THEN 1 ELSE 0 END) AS femaleCount,
        ROUND(SUM(CASE WHEN gender = true THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS malePercentage,
        ROUND(SUM(CASE WHEN gender = false THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS femalePercentage
    FROM users u where u.role <> 'ROLE_ADMIN'
""", nativeQuery = true)
    GenderStatsProjection getGenderStatistics();


    @Query(value = """
        SELECT
            CASE
                WHEN age < 30 THEN '30 ostida'
                WHEN age BETWEEN 30 AND 34 THEN '30-35 yil'
                WHEN age BETWEEN 35 AND 39 THEN '35-40 yil'
                WHEN age BETWEEN 40 AND 44 THEN '40-45 yil'
                WHEN age BETWEEN 45 AND 49 THEN '45-50 yil'
                WHEN age BETWEEN 50 AND 54 THEN '50-55 yil'
                WHEN age BETWEEN 55 AND 59 THEN '55-60 yil'
                ELSE '60+ yil'
            END AS age_group,
            SUM(CASE WHEN gender = true THEN 1 ELSE 0 END) AS male_count,
            SUM(CASE WHEN gender = false THEN 1 ELSE 0 END) AS female_count,
            COUNT(*) AS total,
            ROUND(COUNT(*) * 100.0 / (SELECT COUNT(*) FROM users), 1) AS percentage
        FROM users where role <> 'ROLE_ADMIN'
        GROUP BY age_group
        ORDER BY MIN(age)
    """, nativeQuery = true)
    List<AgeGenderStatsProjection> getAgeGenderStatistics();


    long countByRoleAndEnabledTrue(Role role);



    @Query(value = """
    select u.* from users u join department d on d.id = u.department_id join college c on d.college_id = c.id
    join lavozm l on l.id = u.lavozm_id where u.role <> 'ROLE_ADMIN'
    (:name IS NULL OR LOWER(u.full_name) LIKE LOWER(CONCAT('%', :name, '%'))) AND
    (:college IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :college, '%')) OR
     LOWER(c.name) LIKE LOWER(CONCAT('%', :college, '%'))) AND
    (:lavozim IS NULL OR LOWER(l.name) LIKE LOWER(CONCAT('%', :lavozim, '%'))) order by u.created_at desc
    """, nativeQuery = true)
    Page<User> findAllByUser(@Param("name") String name,
                             @Param("college") String college,
                             @Param("lavozim") String lavozim,
                             Pageable pageable);
}
