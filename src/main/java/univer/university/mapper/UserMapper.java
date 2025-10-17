package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.UserDTO;
import univer.university.entity.User;

@Component
public class UserMapper {
    public UserDTO userDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .phone(user.getPhone())
                .imageUrl(user.getImgUrl())
                .fullName(user.getFullName())
                .build();
    }
}
