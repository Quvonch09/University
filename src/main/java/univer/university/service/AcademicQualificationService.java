package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.AbstractPastInstantBasedValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import univer.university.dto.AcademicQualificationDTO;
import univer.university.dto.ApiResponse;
import univer.university.dto.response.ResPageable;
import univer.university.entity.AcademicQualification;
import univer.university.entity.User;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.AcademicQualificationMapper;
import univer.university.repository.AcademicQualificationRepository;
import univer.university.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AcademicQualificationService {
    private final AcademicQualificationRepository academicQualificationRepository;
    private final UserRepository userRepository;
    private final AcademicQualificationMapper academicQualificationMapper;


    public ApiResponse<String> save(AcademicQualificationDTO academicQualificationDTO){

        User user = userRepository.findById(academicQualificationDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        AcademicQualification academicQualification = AcademicQualification.builder()
                .degreeLevel(academicQualificationDTO.getDegreeLevel())
                .endYear(academicQualificationDTO.getEndYear())
                .startYear(academicQualificationDTO.getStartYear())
                .user(user)
                .institutionName(academicQualificationDTO.getInstitutionName())
                .specialization(academicQualificationDTO.getSpecialization())
                .description(academicQualificationDTO.getDescription())
                .build();
        academicQualificationRepository.save(academicQualification);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String> update(Long id,AcademicQualificationDTO academicQualificationDTO){
        AcademicQualification academicQualification = academicQualificationRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("AcademicQualification not found")
        );

        User user = userRepository.findById(academicQualificationDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );


        academicQualification.setEndYear(academicQualificationDTO.getEndYear());
        academicQualification.setStartYear(academicQualificationDTO.getStartYear());
        academicQualification.setSpecialization(academicQualificationDTO.getSpecialization());
        academicQualification.setDescription(academicQualificationDTO.getDescription());
        academicQualification.setUser(user);
        academicQualification.setInstitutionName(academicQualificationDTO.getInstitutionName());
        academicQualification.setDegreeLevel(academicQualificationDTO.getDegreeLevel());
        academicQualificationRepository.save(academicQualification);
        return ApiResponse.success(null, "Success");
    }


    public ApiResponse<String> delete(Long id){
        AcademicQualification academicQualification = academicQualificationRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("AcademicQualification not found")
        );

        academicQualificationRepository.delete(academicQualification);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<List<AcademicQualificationDTO>> getAll(){
        List<AcademicQualificationDTO> list = academicQualificationRepository.findAll()
                .stream().map(academicQualificationMapper::academicQualificationDTO).toList();

        if (list.isEmpty()){
            return ApiResponse.error("Academic Qualification not found");
        }

        return ApiResponse.success(list, "Success");
    }


    public ApiResponse<AcademicQualificationDTO> getById(Long id){
        AcademicQualification academicQualification = academicQualificationRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("AcademicQualification not found")
        );

        return ApiResponse.success(academicQualificationMapper.academicQualificationDTO(
                academicQualification), "Success");
    }



    public ApiResponse<ResPageable> getByUserId(Long userId, int page, int size){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        Page<AcademicQualification> qualificationPage =
                academicQualificationRepository.findAllByUserId(user.getId(), PageRequest.of(page, size));

        List<AcademicQualificationDTO> list =
                qualificationPage.getContent().stream().map(academicQualificationMapper::academicQualificationDTO).toList();

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(qualificationPage.getTotalPages())
                .totalElements(qualificationPage.getTotalElements())
                .body(list)
                .build();

        return ApiResponse.success(resPageable, "Success");
    }
}
