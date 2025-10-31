package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.AcademicQualificationDTO;
import univer.university.entity.AcademicQualification;

@Component
public class AcademicQualificationMapper {
    public AcademicQualificationDTO academicQualificationDTO(AcademicQualification academicQualification) {
        return AcademicQualificationDTO.builder()
                .id(academicQualification.getId())
                .degreeLevel(academicQualification.getDegreeLevel())
                .startYear(academicQualification.getStartYear())
                .endYear(academicQualification.getEndYear())
                .userId(academicQualification.getUser().getId())
                .institutionName(academicQualification.getInstitutionName())
                .specialization(academicQualification.getSpecialization())
                .description(academicQualification.getDescription())
                .build();
    }
}
