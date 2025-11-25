package univer.university.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.UserDTO;
import univer.university.dto.UserStatisticsDto;
import univer.university.dto.response.*;
import univer.university.entity.User;
import univer.university.entity.enums.AwardEnum;
import univer.university.entity.enums.PublicTypeEnum;
import univer.university.entity.enums.Role;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.UserMapper;
import univer.university.repository.*;
import univer.university.security.JwtService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HttpServletRequest request;
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

    
    
    public ApiResponse<String> update(User existingUser, UserDTO userDTO) {

        existingUser = userRepository.findByIdAndEnabledTrue(userDTO.getId())
                .orElseThrow(() -> new DataNotFoundException("User topilmadi"));

        String oldPhone = existingUser.getPhone();
        String newPhone = userDTO.getPhone();

        if (newPhone == null || newPhone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefon raqami bo‘sh bo‘lishi mumkin emas!");
        }
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setFullName(userDTO.getFullName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setImgUrl(userDTO.getImageUrl());
        User savedUser = userRepository.save(existingUser);

        boolean phoneChanged = !Objects.equals(userDTO.getPhone(), oldPhone);

        String token;

        if (phoneChanged) {
            token = jwtService.generateToken(savedUser.getPhone(), savedUser.getRole().name());
            return ApiResponse.success(token, "success");
        }

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



    public ApiResponse<String> deleteTeacher(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        user.setEnabled(false);
        userRepository.save(user);
        return ApiResponse.success(null, "Success");
    }
}
