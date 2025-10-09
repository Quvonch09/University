package univer.university.component;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import univer.university.entity.User;
import univer.university.entity.enums.Role;
import univer.university.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddl;

    @Override
    public void run(String... args){
        if (ddl.equals("create")){
            User admin = User.builder()
                    .phone("998900000000")
                    .password(encoder.encode("admin123"))
                    .email("admin@gmail.com")
                    .role(Role.ROLE_ADMIN)
                    .fullName("Admin Admin")
                    .enabled(true)
                    .build();

            userRepository.save(admin);
        }
    }
}
