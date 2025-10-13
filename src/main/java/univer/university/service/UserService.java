package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.UserDTO;
import univer.university.dto.request.ReqUserDTO;
import univer.university.entity.Info;
import univer.university.entity.User;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.UserMapper;
import univer.university.repository.InfoRepository;
import univer.university.repository.UserRepository;
import univer.university.security.CustomUserDetailsService;
import univer.university.security.JwtService;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final InfoRepository infoRepository;

    public ApiResponse<UserDTO> getMe(User user){
        return ApiResponse.success(userMapper.userDTO(user), "Success");
    }

    public ApiResponse<String> update(User user, UserDTO userDTO) {

        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new DataNotFoundException("User topilmadi"));

        existingUser.setPhone(userDTO.getPhone());
        existingUser.setFullName(userDTO.getFullName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setImgUrl(userDTO.getImageUrl());
        User savedUser = userRepository.save(existingUser);

        List<Info> infos = infoRepository.getInfosByUserId(user.getId());

        for (Info info : infos){
            if (info.getObject() instanceof Map<?, ?> map) {
                info.setObject(map);
            }
        }
        infoRepository.saveAll(infos);

        if (userDTO.getPhone().equals(user.getPhone())) {
            String token = jwtService.generateToken(
                    savedUser.getPhone(),
                    savedUser.getRole().name()
            );
            return ApiResponse.success(token, savedUser.getRole().name());
        }

        return ApiResponse.success(null, "Success");
    }


}
