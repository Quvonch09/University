package univer.university.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import univer.university.entity.User;
import univer.university.entity.enums.Role;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);

    boolean existsByPhoneAndRole(String phone, Role role);
}
