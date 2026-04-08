package univer.university.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import univer.university.dto.ApiResponse;
import univer.university.dto.UserDTO;
import univer.university.dto.UserStatisticsDto;
import univer.university.dto.request.ReqPassword;
import univer.university.dto.request.ReqTeacher;
import univer.university.dto.request.ReqUserDTO;
import univer.university.dto.response.*;
import univer.university.entity.College;
import univer.university.entity.Department;
import univer.university.entity.Lavozm;
import univer.university.entity.User;
import univer.university.entity.enums.AwardEnum;
import univer.university.entity.enums.PublicTypeEnum;
import univer.university.entity.enums.Role;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.UserMapper;
import univer.university.component.annotation.TrackAction;
import univer.university.entity.enums.ActionType;
import univer.university.repository.*;
import univer.university.security.JwtService;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AwardService awardService;
    private final TadqiqotService tadqiqotService;
    private final PublicationService publicationService;
    private final NazoratService nazoratService;
    private final ConsultationService consultationService;
    private final AcademicQualificationService academicQualificationService;
    private final TadqiqotRepository tadqiqotRepository;
    private final PublicationRepository publicationRepository;
    private final NazoratRepository nazoratRepository;
    private final ConsultationRepository consultationRepository;
    private final AwardRepository awardRepository;
    private final DepartmentRepository departmentRepository;
    private final LavozimRepository lavozimRepository;
    private final CollageRepository collageRepository;
    private final AcademicQualificationRepository academicQualificationRepository;

    public ApiResponse<UserDTO> getMe(User user){
        return ApiResponse.success(userMapper.userDTO(user), "Success");
    }

    public ApiResponse<UserDTO> getById(Long id, int page, int size){
        User user = userRepository.findByIdAndEnabledTrue(id).orElseThrow(() -> new DataNotFoundException("User not found"));
        ResPageable award = awardService.getByUserId(id, page, size).getData();
        ResPageable research = tadqiqotService.getAllByUser(id, page, size).getData();
        ResPageable publication = publicationService.getByUserId(id, page, size).getData();
        ResPageable nazorat = nazoratService.getByUser(id, page, size).getData();
        ResPageable consultation = consultationService.getByUser(id, page, size).getData();
        ResPageable qualification = academicQualificationService.getByUserId(id, page, size).getData();
        return ApiResponse.success(userMapper.userToDTO(user,qualification,research,award,consultation,nazorat,publication), "Success");
    }


    @TrackAction(ActionType.USER_UPDATED)
    public ApiResponse<String> update(User existingUser, ReqUserDTO reqUserDTO) {

        if (reqUserDTO.getId() != null && reqUserDTO.getId() != 0) {
            existingUser = userRepository.findByIdAndEnabledTrue(reqUserDTO.getId())
                    .orElseThrow(() -> new DataNotFoundException("User not found"));
        }

        if (existingUser.getRole().equals(Role.ROLE_ADMIN)){
            return ApiResponse.error("Adminni tahrirlash mumkin emas");
        }

        if (reqUserDTO.getLavozmId() != null && reqUserDTO.getLavozmId() != 0) {
            Lavozm lavozm = lavozimRepository.findById(reqUserDTO.getLavozmId())
                    .orElseThrow(() -> new DataNotFoundException("Lavozm not found"));
            existingUser.setLavozm(lavozm);
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getDepartmentId() != null && reqUserDTO.getDepartmentId() != 0) {
            Department department = departmentRepository.findByIdAndActiveTrue(reqUserDTO.getDepartmentId())
                    .orElseThrow(() -> new DataNotFoundException("Department not found"));
            existingUser.setDepartment(department);
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getFullName() != null && !reqUserDTO.getFullName().isBlank()) {
            existingUser.setFullName(reqUserDTO.getFullName());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getEmail() != null && !reqUserDTO.getEmail().isBlank()) {
            existingUser.setEmail(reqUserDTO.getEmail());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getImageUrl() != null) {
            existingUser.setImgUrl(reqUserDTO.getImageUrl());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getAge() != null) {
            existingUser.setAge(reqUserDTO.getAge());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getBiography() != null) {
            existingUser.setBiography(reqUserDTO.getBiography());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getGender() != null) {
            existingUser.setGender(reqUserDTO.getGender());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getFileUrl() != null) {
            existingUser.setFileUrl(reqUserDTO.getFileUrl());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getProfession() != null) {
            existingUser.setProfession(reqUserDTO.getProfession());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getInput() != null) {
            existingUser.setInput(reqUserDTO.getInput());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getOrcId() != null) {
            existingUser.setOrcId(reqUserDTO.getOrcId());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getResearcherId() != null) {
            existingUser.setResearcherId(reqUserDTO.getResearcherId());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getScopusId() != null) {
            existingUser.setScopusId(reqUserDTO.getScopusId());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getScienceId() != null) {
            existingUser.setScienceId(reqUserDTO.getScienceId());
            userRepository.save(existingUser);
        }

        if (reqUserDTO.getPhoneNumber() != null &&
                !reqUserDTO.getPhoneNumber().equals(existingUser.getPhone())) {
            existingUser.setPhone(reqUserDTO.getPhoneNumber());
            userRepository.save(existingUser);
        }

        userRepository.save(existingUser);

        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<ResDashboard> getDashboard(){
        long teacherCount = userRepository.countByRoleAndEnabledTrue(Role.ROLE_TEACHER.name());
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



    public ApiResponse<ResPageable> searchUsers(String name, String college, String lavozim, int page, int size){
        Page<User> userPage = userRepository.findAllByUser(name, college, lavozim, PageRequest.of(page, size));
        if (userPage.getTotalElements() == 0){
            return ApiResponse.error("User not found");
        }

        List<ResUser> list = userPage.getContent().stream().map(userMapper::resUser).toList();

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(userPage.getTotalPages())
                .totalElements(userPage.getTotalElements())
                .body(list)
                .build();

        return ApiResponse.success(resPageable, "Success");
    }


    public ApiResponse<List<ResUser>> getUsersDepartmentId(Long departmentId){
        Department department = departmentRepository.findByIdAndActiveTrue(departmentId).orElseThrow(
                () -> new DataNotFoundException("Department not found")
        );

        List<ResUser> list = userRepository.findAllByDepartmentIdAndEnabledTrue(department.getId()).stream().map(userMapper::resUser).toList();
        return ApiResponse.success(list, "Success");
    }



    public ApiResponse<List<ResUser>> getUsersCollegeId(Long collegeId){
        College college = collageRepository.findByIdAndActiveTrue(collegeId).orElseThrow(
                () -> new DataNotFoundException("College not found")
        );

        List<ResUser> list = userRepository.findAllByCollegeId(college.getId()).stream().map(userMapper::resUser).toList();
        return ApiResponse.success(list, "Success");
    }






    public ApiResponse<UserStatisticsDto> getUserDashboard(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        long countTadqiqot = tadqiqotRepository.countAllByUserId(user.getId());
        long countBook = publicationRepository.countAllByUserIdAndType(user.getId(), PublicTypeEnum.BOOK);
        long countArticle = publicationRepository.countAllByUserIdAndType(user.getId(), PublicTypeEnum.ARTICLE);
        long countProceeding = publicationRepository.countAllByUserIdAndType(user.getId(), PublicTypeEnum.PROCEEDING);
        long countOthers = publicationRepository.countAllByUserIdAndType(user.getId(), PublicTypeEnum.OTHERS);
        long countPublication = publicationRepository.countAllByUserId(user.getId());
        long countNazorat = nazoratRepository.countAllByUserId(user.getId());
        long countConsultation = consultationRepository.countAllByUserId(user.getId());
        long countAward = awardRepository.countAllByUserId(user.getId());
        long countTrening = awardRepository.countAllByUserIdAndAwardEnum(user.getId(), AwardEnum.Trening_Va_Amaliyot);
        long countTahririyat = awardRepository.countAllByUserIdAndAwardEnum(user.getId(), AwardEnum.Tahririyat_Kengashiga_Azolik);
        long countMaxsus = awardRepository.countAllByUserIdAndAwardEnum(user.getId(), AwardEnum.Maxsus_Kengash_Azoligi);
        long countPatent = awardRepository.countAllByUserIdAndAwardEnum(user.getId(), AwardEnum.Patent_Dgu);
        long countDavlat = awardRepository.countAllByUserIdAndAwardEnum(user.getId(), AwardEnum.Davlat_Mukofoti);


        UserStatisticsDto userStatisticsDto = UserStatisticsDto.builder()
                .boshqalar(countOthers)
                .tadqiqotlar(countTadqiqot)
                .davlatMukofotlari(countDavlat)
                .ishYuritishlar(countProceeding)
                .kitoblar(countBook)
                .maqolalar(countArticle)
                .maslahatlar(countConsultation)
                .nashrlar(countPublication)
                .maxsusKengash(countMaxsus)
                .mukofotlar(countAward)
                .nazorat(countNazorat)
                .treninglar(countTrening)
                .tahririyatAzolik(countTahririyat)
                .patentlar(countPatent)
                .build();

        return ApiResponse.success(userStatisticsDto, "Success");
    }



    @TrackAction(ActionType.TEACHER_DELETED)
    public ApiResponse<String> deleteTeacher(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );
        
        if (user.getRole().equals(Role.ROLE_ADMIN)){
            return ApiResponse.error("Adminni o'chirish mumkin emas");
        }

        user.setLavozm(null);
        user.setEnabled(false);
        userRepository.save(user);
        return ApiResponse.success(null, "Success");
    }


    @TrackAction(ActionType.TEACHER_UPDATED)
    @Transactional
    public ApiResponse<String> updateUser(ReqTeacher dto) {

        User existingUser = userRepository.findByIdAndEnabledTrue(dto.getId()).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        if (existingUser.getRole().equals(Role.ROLE_ADMIN)){
            return ApiResponse.error("Adminni tahrirlash mumkin emas");
        }

        // fullName
        if (dto.getFullName() != null && !dto.getFullName().isBlank()) {
            existingUser.setFullName(dto.getFullName());
        }

        // phone
        if (dto.getPhoneNumber() != null && !dto.getPhoneNumber().isBlank()
                && !dto.getPhoneNumber().equals(existingUser.getPhone())) {

            boolean exists = userRepository.existsByPhone(dto.getPhoneNumber());
            if (exists) {
                throw new RuntimeException("Phone already exists");
            }

            existingUser.setPhone(dto.getPhoneNumber());
        }

        // image
        if (dto.getImgUrl() != null) {
            existingUser.setImgUrl(dto.getImgUrl());
        }

        // file
        if (dto.getFileUrl() != null) {
            existingUser.setFileUrl(dto.getFileUrl());
        }

        // gender
        existingUser.setGender(dto.isGender());

        // lavozm
        if (dto.getLavozmId() != null) {
            Lavozm lavozm = lavozimRepository.findById(dto.getLavozmId())
                    .orElseThrow(() -> new DataNotFoundException("Lavozm not found"));

            existingUser.setLavozm(lavozm);
        }

        // department
        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findByIdAndActiveTrue(dto.getDepartmentId())
                    .orElseThrow(() -> new DataNotFoundException("Department not found"));

            existingUser.setDepartment(department);
        }

        // password
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        userRepository.save(existingUser);

        return ApiResponse.success(null, "User updated successfully");
    }

    public ApiResponse<Double> getProfileCompletionPercentage(Long userId) {
        User user = userRepository.findByIdAndEnabledTrue(userId).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        double totalPoints = 22.0;
        double filledPoints = 0.0;

        // 1. User properties (16 units)
        if (user.getFullName() != null && !user.getFullName().isBlank()) filledPoints++;
        if (user.getPhone() != null && !user.getPhone().isBlank()) filledPoints++;
        if (user.getEmail() != null && !user.getEmail().isBlank()) filledPoints++;
        if (user.getBiography() != null && !user.getBiography().isBlank()) filledPoints++;
        if (user.getAge() > 0) filledPoints++;
        if (user.getImgUrl() != null && !user.getImgUrl().isBlank()) filledPoints++;
        if (user.getFileUrl() != null && !user.getFileUrl().isBlank()) filledPoints++;
        if (user.getInput() != null && !user.getInput().isBlank()) filledPoints++;
        if (user.getProfession() != null && !user.getProfession().isBlank()) filledPoints++;
        if (user.getOrcId() != null && !user.getOrcId().isBlank()) filledPoints++;
        if (user.getScopusId() != null && !user.getScopusId().isBlank()) filledPoints++;
        if (user.getScienceId() != null && !user.getScienceId().isBlank()) filledPoints++;
        if (user.getResearcherId() != null && !user.getResearcherId().isBlank()) filledPoints++;
        // Gender is boolean primitive, so it always has a value (true/false)
        filledPoints++; 
        if (user.getDepartment() != null) filledPoints++;
        if (user.getLavozm() != null) filledPoints++;

        // 2. Related data presence (6 units)
//        if (tadqiqotRepository.countAllByUserId(userId) > 0) filledPoints++;
//        if (publicationRepository.countAllByUserId(userId) > 0) filledPoints++;
//        if (awardRepository.countAllByUserId(userId) > 0) filledPoints++;
//        if (consultationRepository.countAllByUserId(userId) > 0) filledPoints++;
//        if (nazoratRepository.countAllByUserId(userId) > 0) filledPoints++;
//        if (academicQualificationRepository.countAllByUserId(userId) > 0) filledPoints++;

        double percentage = (filledPoints / totalPoints) * 100;
        return ApiResponse.success(Math.round(percentage * 100.0) / 100.0, "Success");
    }

    public ApiResponse<String> updatePassword(User user, ReqPassword reqPassword){
        if (reqPassword.getUserId() != 0 || reqPassword.getPassword() == null){
            user = userRepository.findById(reqPassword.getUserId()).orElseThrow(
                    () -> new DataNotFoundException("User topilmadi")
            );

            user.setPassword(passwordEncoder.encode(reqPassword.getPassword()));
            userRepository.save(user);
            return ApiResponse.success(null, "Success");
        }

        user.setPassword(passwordEncoder.encode(reqPassword.getPassword()));
        User save = userRepository.save(user);
        String token = jwtService.generateToken(
                save.getUsername(),
                save.getRole().name()
        );
        return ApiResponse.success(token, "Success");
    }

}
