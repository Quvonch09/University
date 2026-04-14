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
    boolean existsByPhone(String phone);
    boolean existsByLavozm_Id(Long id);
    boolean existsByIlmiyDaraja_Id(Long id);

    Optional<User> findByIdAndEnabledTrue(Long id);

    List<User> findAllByDepartmentIdAndEnabledTrue(Long departmentId);


    // 1. College bo‘yicha userlarni olish (o‘zgarmagan)
    @Query(value = """
    select u.* from users u 
    join department d on d.id = u.department_id 
    join college c on c.id = d.college_id 
    where c.id = ?1 and u.enabled = true
    """, nativeQuery = true)
    List<User> findAllByCollegeId(Long collegeId);


    // 2. College bo‘yicha userlar soni (o‘zgarmagan)
    @Query(value = """
    select count(u.*) from users u 
    join department d on u.department_id = d.id
    join college c on d.college_id = c.id 
    where c.id = ?1 and c.active = true and u.enabled = true
    """, nativeQuery = true)
    long countByCollege(Long collegeId);


    // 3. Department bo‘yicha userlar soni (o‘zgarmagan)
    @Query(value = """
    select count(u.*) from users u 
    join department d on u.department_id = d.id 
    where d.id = ?1 and d.active = true and u.enabled = true
    """, nativeQuery = true)
    long countByDepartment(Long departmentId);


    // 4. College bo‘yicha ilmiy daraja hisoblash (academic_qualification bilan)
    @Query(value = """
    select count(u.id) 
    from users u
    join department d on d.id = u.department_id
    join college c on c.id = d.college_id
    join lavozm l on l.id = u.lavozm_id
    where c.id = ?1 
      and lower(l.name) = lower(?2)
      and u.enabled = true
    """, nativeQuery = true)
    long countAcademicByCollege(Long collegeId, String academicTitle);


    // 5. Department bo‘yicha ilmiy daraja hisoblash (academic_qualification bilan)
    @Query( value = """
    select count(u.id)
    from users u
    join lavozm l on l.id = u.lavozm_id
    where u.department_id = ?1
      and lower(l.name) = lower(?2)
      and u.enabled = true
    """, nativeQuery = true)
    long countAcademicByDepartment(Long departmentId, String academicTitle);


    // 6. College bo‘yicha level hisoblash (level academic_qualification.degree_level deb olinmoqda)
    @Query(value = """
    select count(distinct u.id) from users u
    join department d on u.department_id = d.id
    join college c on d.college_id = c.id
    join academic_qualification aq on u.id = aq.user_id
    where c.id = ?1 
      and aq.degree_level = ?2
      and c.active = true 
      and u.enabled = true
    """, nativeQuery = true)
    long countLevelByCollege(Long collegeId, String level);


    // 7. Department bo‘yicha level hisoblash (academic_qualification orqali)
    @Query(value = """
    select count(distinct u.id) from users u
    join academic_qualification aq on u.id = aq.user_id
    join department d on d.id = u.department_id
    where d.id = ?1 
      and aq.degree_level = ?2
      and d.active = true 
      and u.enabled = true
    """, nativeQuery = true)
    long countLevelByDepartment(Long departmentId, String level);


    // 8. College bo‘yicha ilmiy ma’lumotlari yo‘q userlar (NONE)
    @Query( value = """
    select count(u.id)
    from users u
    left join academic_qualification aq on aq.user_id = u.id
    join department d on d.id = u.department_id
    join college c on c.id = d.college_id
    where c.id = ?1
      and aq.id is null
      and u.enabled = true
    """, nativeQuery = true)
    long countNoAcademicByCollege(Long collegeId);



    // 9. Department bo‘yicha ilmiy ma’lumoti yo‘q userlar
    @Query(value = """
    select count(u.id) from users u
    left join academic_qualification aq on u.id = aq.user_id
    where u.department_id = ?1 
      and aq.id is null 
      and u.enabled = true
    """, nativeQuery = true)
    long countByDepartmentNone(Long departmentId);


    // 10. Role bo‘yicha sanash (o‘zgarmagan)
    @Query(value = """
    select count(*) from users 
    where role = ?1 and enabled = true
    """, nativeQuery = true)
    long countByRoleAndEnabledTrue(String role);


    // 11. Gender bo‘yicha sanash (o‘zgarmagan)
    @Query(value = """
    select count(u.*) from users u 
    where u.gender = ?1 
      and u.role <> 'ROLE_ADMIN' 
      and u.enabled = true
    """, nativeQuery = true)
    long countByGender(boolean gender);


    // 12. Academic qualification bor userlar soni (o‘zgarmagan)
    @Query(value = """
    select count(distinct u.id) from users u 
    join academic_qualification aq on u.id = aq.user_id
    where aq.degree_level IS NOT NULL 
      and aq.specialization IS NOT NULL 
      and u.enabled = true
    """, nativeQuery = true)
    long countByAcademic();

    @Query(value = """
    SELECT
        COUNT(*) AS total,
        SUM(CASE WHEN gender = true THEN 1 ELSE 0 END) AS maleCount,
        SUM(CASE WHEN gender = false THEN 1 ELSE 0 END) AS femaleCount,
        ROUND(SUM(CASE WHEN gender = true THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS malePercentage,
        ROUND(SUM(CASE WHEN gender = false THEN 1 ELSE 0 END) * 100.0 / COUNT(*), 2) AS femalePercentage
    FROM users u where u.role <> 'ROLE_ADMIN' and u.enabled = true
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
        FROM users where role <> 'ROLE_ADMIN' and enabled = true
        GROUP BY age_group
        ORDER BY MIN(age)
    """, nativeQuery = true)
    List<AgeGenderStatsProjection> getAgeGenderStatistics();


    long countByRoleAndEnabledTrue(Role role);



    @Query(value = """
    SELECT *
    FROM (
        SELECT u.*,

        (
            CASE 
                WHEN 
                    COALESCE(u.full_name, '') <> '' AND
                    COALESCE(u.phone, '') <> '' AND
                    COALESCE(u.email, '') <> '' AND
                    COALESCE(u.biography, '') <> '' AND
                    u.age <> 0 AND
                    COALESCE(u.profession, '') <> '' AND
                    u.department_id IS NOT NULL AND
                    u.lavozm_id IS NOT NULL AND
                    COALESCE(u.orc_id, '') <> '' AND
                    COALESCE(u.researcher_id, '') <> '' AND
                    COALESCE(u.file_url, '') <> '' AND
                    COALESCE(u.img_url, '') <> '' AND
                    COALESCE(u.science_id, '') <> '' AND
                    COALESCE(u.scopus_id, '') <> '' AND
                    COALESCE(u.input, '') <> '' AND
                    u.ilmiy_daraja_id IS NOT NULL
                THEN 1 ELSE 0
            END
        ) as profile_complete,

        (
            (CASE WHEN COALESCE(u.full_name, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN COALESCE(u.phone, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN COALESCE(u.email, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN COALESCE(u.biography, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN u.age <> 0 THEN 1 ELSE 0 END) +
            (CASE WHEN COALESCE(u.profession, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN u.department_id IS NOT NULL THEN 1 ELSE 0 END) +
            (CASE WHEN u.lavozm_id IS NOT NULL THEN 1 ELSE 0 END) +
            (CASE WHEN COALESCE(u.orc_id, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN COALESCE(u.researcher_id, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN COALESCE(u.file_url, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN COALESCE(u.img_url, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN COALESCE(u.science_id, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN COALESCE(u.scopus_id, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN COALESCE(u.input, '') <> '' THEN 1 ELSE 0 END) +
            (CASE WHEN u.ilmiy_daraja_id IS NOT NULL THEN 1 ELSE 0 END)
        ) as profile_score,

        COUNT(DISTINCT t.id) as tadqiqot_count,
        COUNT(DISTINCT p.id) as publication_count,
        COUNT(DISTINCT cs.id) as consultation_count,
        COUNT(DISTINCT a.id) as award_count

        FROM users u
        JOIN department d ON d.id = u.department_id
        JOIN college c ON c.id = d.college_id
        JOIN lavozm l ON l.id = u.lavozm_id

        LEFT JOIN tadqiqot t ON t.user_id = u.id
        LEFT JOIN publication p ON p.user_id = u.id
        LEFT JOIN consultation cs ON cs.user_id = u.id
        LEFT JOIN award a ON a.user_id = u.id

        WHERE u.role <> 'ROLE_ADMIN'
          AND u.enabled = true

          AND (:name IS NULL OR LOWER(u.full_name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:college IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :college, '%')) 
               OR LOWER(c.name) LIKE LOWER(CONCAT('%', :college, '%')))
          AND (:lavozim IS NULL OR LOWER(l.name) LIKE LOWER(CONCAT('%', :lavozim, '%')))

        GROUP BY u.id
    ) as result

    ORDER BY
        profile_complete DESC,
        profile_score DESC,
        tadqiqot_count DESC,
        publication_count DESC,
        consultation_count DESC,
        award_count DESC,
        created_at DESC
    """,

            countQuery = """
        SELECT COUNT(DISTINCT u.id)
        FROM users u
        JOIN department d ON d.id = u.department_id
        JOIN college c ON c.id = d.college_id
        JOIN lavozm l ON l.id = u.lavozm_id

        WHERE u.role <> 'ROLE_ADMIN'
          AND u.enabled = true

          AND (:name IS NULL OR LOWER(u.full_name) LIKE LOWER(CONCAT('%', :name, '%')))
          AND (:college IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%', :college, '%')) 
               OR LOWER(c.name) LIKE LOWER(CONCAT('%', :college, '%')))
          AND (:lavozim IS NULL OR LOWER(l.name) LIKE LOWER(CONCAT('%', :lavozim, '%')))
    """,

            nativeQuery = true)
    Page<User> findAllByUser(
            @Param("name") String name,
            @Param("college") String college,
            @Param("lavozim") String lavozim,
            Pageable pageable);


    List<User> findAllByIlmiyDaraja_Id(Long ilmiyDarajaId);
}
