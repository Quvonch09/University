package univer.university.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import univer.university.entity.User;

@Component
@RequiredArgsConstructor
public class SecurityUtil {

    public CurrentActor getCurrentActor() {

        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return CurrentActor.anonymous();
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof User user) {
            return CurrentActor.builder()
                    .id(user.getId())
                    .type("USER")
                    .role(user.getRole().name())
                    .fullName(user.getFullName())
                    .build();
        }

        return CurrentActor.anonymous();
    }
}