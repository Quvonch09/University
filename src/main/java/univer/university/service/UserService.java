package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.UserDTO;
import univer.university.entity.User;
import univer.university.mapper.UserMapper;
import univer.university.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ApiResponse<UserDTO> getMe(User user){
        return ApiResponse.success(userMapper.userDTO(user), "Success");
    }

}
