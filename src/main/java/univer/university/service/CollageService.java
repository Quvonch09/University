package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.CollegeDTO;
import univer.university.dto.request.ReqCollage;
import univer.university.dto.response.ResCollage;
import univer.university.dto.response.ResDepartment;
import univer.university.entity.College;
import univer.university.entity.Department;
import univer.university.entity.enums.AcademicTitle;
import univer.university.entity.enums.Level;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.CollageMapper;
import univer.university.mapper.DepartmentMapper;
import univer.university.repository.CollageRepository;
import univer.university.repository.DepartmentRepository;
import univer.university.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollageService {
    private final CollageRepository collageRepository;
    private final DepartmentRepository departmentRepository;
    private final CollageMapper collageMapper;
    private final UserRepository userRepository;
    private final DepartmentMapper departmentMapper;

    public ApiResponse<String> saveCollage(ReqCollage reqCollage){
        boolean b = collageRepository.existsByName(reqCollage.getName());
        if (b){
            return ApiResponse.error("This collage already exists");
        }

        College college = College.builder()
                .name(reqCollage.getName())
                .imgUrl(reqCollage.getImgUrl())
                .active(true)
                .build();
        collageRepository.save(college);
        return ApiResponse.success(null, "Successfully saved collage");
    }



    public ApiResponse<String> updateCollage(Long collageId,ReqCollage reqCollage){
        boolean b = collageRepository.existsByNameAndIdNot(reqCollage.getName(), collageId);
        if (b){
            return ApiResponse.error("This collage already exists");
        }

        College college = collageRepository.findById(collageId).orElseThrow(
                () -> new DataNotFoundException("This collage does not exist")
        );
        college.setName(reqCollage.getName());
        college.setImgUrl(reqCollage.getImgUrl());
        collageRepository.save(college);
        return ApiResponse.success(null, "Successfully updated collage");
    }


    public ApiResponse<String> deleteCollage(Long collageId){
        College college = collageRepository.findById(collageId).orElseThrow(
                () -> new DataNotFoundException("This collage does not exist")
        );
        collageRepository.delete(college);
        return ApiResponse.success(null, "Successfully deleted collage");
    }


//  getAll
    public ApiResponse<List<ResCollage>> getCollage(){
        List<College> collegeList = collageRepository.findAll();

        List<ResCollage> dtoList = new ArrayList<>();

        for (College college : collegeList) {

            List<Department> allByCollegeId = departmentRepository.findAllByCollegeId(college.getId());

            List<String> departmentNames = allByCollegeId.stream().map(Department::getName).toList();

            ResCollage dto = collageMapper.toDTO(college, departmentNames);

            dtoList.add(dto);
        }

        return ApiResponse.success(dtoList, "Success");
    }



    public ApiResponse<CollegeDTO> getOneCollege(Long collegeId){
        College college = collageRepository.findById(collegeId).orElseThrow(
                () -> new DataNotFoundException("College not found")
        );

        long countAllUsers = userRepository.countByCollege(college.getId());
        long countPHD = userRepository.countLevelByCollege(college.getId(), Level.FAN_NOMZODI_PhD.name());
        long countDSC = userRepository.countLevelByCollege(college.getId(), Level.FAN_DOKTORI_DSc.name());
        long countProfessor = userRepository.countAcademicByCollege(college.getId(), AcademicTitle.PROFESSOR.name());
        long countDotsent = userRepository.countAcademicByCollege(college.getId(), AcademicTitle.DOTSENT.name());
        long countNull = userRepository.countByCollegeNone(college.getId());

        List<ResDepartment> departmentList = departmentRepository.findAllByCollegeId(college.getId())
                .stream().map(departmentMapper::toResDTO).toList();

        CollegeDTO collegeDTO = CollegeDTO.builder()
                .id(college.getId())
                .name(college.getName())
                .countUsers(countAllUsers)
                .countPHD(countPHD)
                .countDSC(countDSC)
                .countProfessor(countProfessor)
                .countDotsent(countDotsent)
                .countNull(countNull)
                .departmentList(departmentList)
                .build();

        return ApiResponse.success(collegeDTO, "Success");
    }
}
