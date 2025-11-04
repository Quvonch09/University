package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.AuthRegister;
import univer.university.entity.Department;
import univer.university.entity.Lavozm;
import univer.university.entity.User;
import univer.university.entity.UserInfo;
import univer.university.entity.enums.AcademicTitle;
import univer.university.entity.enums.Level;
import univer.university.entity.enums.Role;
import univer.university.exception.DataNotFoundException;
import univer.university.repository.DepartmentRepository;
import univer.university.repository.LavozimRepository;
import univer.university.repository.UserInfoRepository;
import univer.university.repository.UserRepository;
import univer.university.security.JwtService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;
    private final UserInfoRepository userInfoRepository;
    private final LavozimRepository lavozimRepository;

    public ApiResponse<String> login(String phone, String password) {
        Optional<User> optionalUser = userRepository.findByPhone(phone);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.isEnabled()){
                return ApiResponse.error("Bu user faol emas. Adminlarga murojaat qiling");
            }

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






    public ApiResponse<String> saveUser(AuthRegister authRegister){

        boolean b = userRepository.existsByPhoneAndRole(authRegister.getPhoneNumber(), Role.ROLE_TEACHER);
        if (b){
            return ApiResponse.error("Teacher already exists");
        }

        Department department = departmentRepository.findById(authRegister.getDepartmentId()).orElseThrow(
                () -> new DataNotFoundException("Department not found")
        );

        Lavozm lavozm = lavozimRepository.findById(authRegister.getLavozmId()).orElseThrow(
                () -> new DataNotFoundException("Lavozim not found")
        );


        User teacher = User.builder()
                .phone(authRegister.getPhoneNumber())
                .fullName(authRegister.getFullName())
                .biography(authRegister.getBiography())
                .imgUrl(authRegister.getImgUrl())
                .password(passwordEncoder.encode(authRegister.getPassword()))
                .role(Role.ROLE_TEACHER)
                .department(department)
                .input(authRegister.getInput())
                .lavozm(lavozm)
                .enabled(true)
                .fileUrl(authRegister.getFileUrl())
                .profession(authRegister.getProfession())
                .email(authRegister.getEmail())
                .age(authRegister.getAge())
                .gender(authRegister.isGender())
                .build();
        userRepository.save(teacher);

//        UserInfo userInfo = UserInfo.builder()
//                .user(save)
//                .academicTitle(academicTitle)
//                .level(level)
//                .build();
//        userInfoRepository.save(userInfo);

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
