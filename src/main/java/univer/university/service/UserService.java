package univer.university.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.UserDTO;
import univer.university.dto.request.ReqUserDTO;
import univer.university.dto.response.AgeGenderStatsProjection;
import univer.university.dto.response.GenderStatsProjection;
import univer.university.dto.response.ResDashboard;
import univer.university.entity.Info;
import univer.university.entity.User;
import univer.university.entity.enums.Role;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.UserMapper;
import univer.university.repository.InfoRepository;
import univer.university.repository.UserRepository;
import univer.university.security.JwtService;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HttpServletRequest request;

    public ApiResponse<UserDTO> getMe(User user){
        return ApiResponse.success(userMapper.userDTO(user), "Success");
    }

    public ApiResponse<String> update(User user, UserDTO userDTO) {

        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new DataNotFoundException("User topilmadi"));

        String oldPhone = existingUser.getPhone();
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setFullName(userDTO.getFullName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setImgUrl(userDTO.getImageUrl());
        User savedUser = userRepository.save(existingUser);

        String token = null;
        boolean phoneChanged = !Objects.equals(userDTO.getPhone(),oldPhone);

        if (phoneChanged) {
            token = jwtService.generateToken(savedUser.getPhone(), savedUser.getRole().name());
        } else {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            } else {
                token = jwtService.generateToken(savedUser.getPhone(), savedUser.getRole().name());
            }
        }

        return ApiResponse.success(token, "Success");
    }



    public ApiResponse<ResDashboard> getDashboard(){
        long teacherCount = userRepository.countByRole(Role.ROLE_TEACHER);
        long maleCount = userRepository.countByGender(true);
        long feMaleCount = userRepository.countByGender(false);
        long countByAcademic = userRepository.countByAcademic();

        ResDashboard resDashboard = ResDashboard.builder()
                .countAllUsers(teacherCount)
                .countMale(maleCount)
                .countFemale(feMaleCount)
                .countAcademic(countByAcademic)
                .build();

        return ApiResponse.success(resDashboard, "Success");
    }


    public ApiResponse<GenderStatsProjection> getGenderDashboard(){
        GenderStatsProjection genderStatistics = userRepository.getGenderStatistics();
        return ApiResponse.success(genderStatistics, "Success");
    }



    public ApiResponse<List<AgeGenderStatsProjection>> getAgeGenderDashboard(){
        List<AgeGenderStatsProjection> ageGenderStatistics = userRepository.getAgeGenderStatistics();
        return ApiResponse.success(ageGenderStatistics, "Success");
    }

}
