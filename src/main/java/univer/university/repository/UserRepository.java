package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import univer.university.entity.User;
import univer.university.entity.enums.Role;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);

    boolean existsByPhoneAndRole(String phone, Role role);


    @Query(value = """
    select count(u.*) from users u join department d on u.department_id = d.id
        join college c on d.college_id = c.id where c.id = ?1
    """, nativeQuery = true)
    long countByCollege(Long collegeId);


    @Query(value = """
    select count(u.*) from users u where u.department_id = ?1
    """, nativeQuery = true)
    long countByDepartment(Long departmentId);


    @Query(value = """
    select count(u.*) from users u join department d on u.department_id = d.id 
        join college c on d.college_id = c.id join user_info uf on u.id = uf.user_id 
        where c.id = ?1 and uf.academic_title = ?2
    """, nativeQuery = true)
    long countAcademicByCollege(Long collegeId, String academicTitle);


    @Query(value = """
    select count(u.*) from users u join user_info uf on u.id = uf.user_id
     where u.department_id = ?1 and uf.academic_title = ?2
    """, nativeQuery = true)
    long countAcademicByDepartment(Long departmentId, String academicTitle);


    @Query(value = """
    select count(u.*) from users u join department d on u.department_id = d.id join college c on d.college_id = c.id
    join user_info uf on u.id = uf.user_id where c.id = ?1 and uf.level = ?2
    """, nativeQuery = true)
    long countLevelByCollege(Long collegeId, String level);


    @Query(value = """
    select count(u.*) from users u join user_info uf on u.id = uf.user_id where u.department_id = ?1 and uf.level = ?2
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
}
