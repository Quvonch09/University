package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.AuthRegister;
import univer.university.entity.User;
import univer.university.entity.enums.Role;
import univer.university.repository.UserRepository;
import univer.university.security.JwtService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<String> login(String phone, String password) {
        Optional<User> optionalUser = userRepository.findByPhone(phone);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return ApiResponse.error("Invalid password");
            }

            String token = jwtService.generateToken(
                    user.getUsername(),
                    user.getRole().name()
            );

            return ApiResponse.success(token, user.getRole().name());
        }

        return ApiResponse.error("User topilmadi");
    }






    public ApiResponse<String> saveUser(AuthRegister authRegister, Role role){

        boolean b = userRepository.existsByPhoneAndRole(authRegister.getPhoneNumber(), role);
        if (b){
            return ApiResponse.error("Teacher already exists");
        }

        User teacher = User.builder()
                .phone(authRegister.getPhoneNumber())
                .fullName(authRegister.getFullName())
                .password(passwordEncoder.encode(authRegister.getPassword()))
                .role(role)
                .build();
        userRepository.save(teacher);
        return ApiResponse.success(null, "Successfully added user");
    }


//    public ApiResponse<String> saveStudent(ReqStudent reqStudent){
//
//        boolean b = studentRepository.existsByPhoneNumber(reqStudent.getPhone());
//        boolean b1 = userRepository.existsByPhone(reqStudent.getPhone());
//
//        if (b || b1){
//            return ApiResponse.error("User already exists");
//        }
//
//        User parent = userRepository.findByPhoneAndRole(reqStudent.getParentPhone(), Role.PARENT).orElseThrow(
//                () -> new DataNotFoundException("Parent not found")
//        );
//
//        Group group = groupRepository.findById(reqStudent.getGroupId()).orElseThrow(
//                () -> new DataNotFoundException("Group not found")
//        );
//
//        Student student = Student.builder()
//                .fullName(reqStudent.getFullName())
//                .parent(parent)
//                .phoneNumber(reqStudent.getPhone())
//                .password(passwordEncoder.encode(reqStudent.getPassword()))
//                .group(group)
//                .imgUrl(reqStudent.getImgUrl())
//                .build();
//        studentRepository.save(student);
//        return ApiResponse.success(null, "Successfully saved student");
//    }
}
