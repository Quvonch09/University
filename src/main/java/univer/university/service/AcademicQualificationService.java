package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.time.past.AbstractPastInstantBasedValidator;
import org.springframework.stereotype.Service;
import univer.university.dto.AcademicQualificationDTO;
import univer.university.dto.ApiResponse;
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
}
