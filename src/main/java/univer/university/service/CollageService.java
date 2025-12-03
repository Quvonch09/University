package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.CollegeDTO;
import univer.university.dto.request.ReqCollage;
import univer.university.dto.response.ResCollage;
import univer.university.dto.response.ResCollegeDashboard;
import univer.university.dto.response.ResDepartment;
import univer.university.dto.response.ResPageable;
import univer.university.entity.College;
import univer.university.entity.Department;
import univer.university.entity.enums.AcademicTitle;
import univer.university.entity.enums.Level;
import univer.university.entity.enums.Role;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.CollageMapper;
import univer.university.mapper.DepartmentMapper;
import univer.university.repository.CollageRepository;
import univer.university.repository.DepartmentRepository;
import univer.university.repository.InfoRepository;
import univer.university.repository.UserRepository;

import java.time.Instant;
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
    private final InfoRepository infoRepository;

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

        for (Department department : departmentRepository.findAllByCollegeIdAndActiveTrue(college.getId())) {
            department.setActive(false);
            departmentRepository.save(department);
        }

        college.setActive(false);
        collageRepository.save(college);
        return ApiResponse.success(null, "Successfully deleted collage");
    }


//  getAll
    public ApiResponse<List<ResCollage>> getCollage(String name){
        List<College> collegeList = collageRepository.searchAllByNameLikeAndActiveTrue(name);

        if (collegeList.isEmpty()){
            return ApiResponse.error("College not found");
        }

        List<ResCollage> dtoList = new ArrayList<>();

        for (College college : collegeList) {

            List<Department> allByCollegeId = departmentRepository.findAllByCollegeIdAndActiveTrue(college.getId());

            List<String> departmentNames = allByCollegeId.stream().map(Department::getName).toList();

            ResCollage dto = collageMapper.toDTO(college, departmentNames);

            dtoList.add(dto);
        }

        return ApiResponse.success(dtoList, "Success");
    }



    public ApiResponse<CollegeDTO> getOneCollege(Long collegeId){
        College college = collageRepository.findByIdAndActiveTrue(collegeId).orElseThrow(
                () -> new DataNotFoundException("College not found")
        );

        long countAllUsers = userRepository.countByCollege(college.getId());
        long countPHD = userRepository.countLevelByCollege(college.getId(), Level.FAN_NOMZODI_PhD.name());
        long countDSC = userRepository.countLevelByCollege(college.getId(), Level.FAN_DOKTORI_DSc.name());
        long countProfessor = userRepository.countAcademicByCollege(college.getId(), "Professor");
        long countDotsent = userRepository.countAcademicByCollege(college.getId(), "Dotsent");
        long countNull = userRepository.countNoAcademicByCollege(college.getId());

        List<ResDepartment> departmentList = departmentRepository.findAllByCollegeIdAndActiveTrue(college.getId())
                .stream().map(departmentMapper::toResDTO).toList();

        CollegeDTO collegeDTO = CollegeDTO.builder()
                .id(college.getId())
                .name(college.getName())
                .imgUrl(college.getImgUrl())
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


    public ApiResponse<ResCollegeDashboard> getCollegeDashboard(){
        long countTeacher = userRepository.countByRoleAndEnabledTrue(Role.ROLE_TEACHER);
        long departmentCount = departmentRepository.count();
        long countInfo = infoRepository.countInfoByCreatedAt(Instant.now());
        long count = infoRepository.count();

        ResCollegeDashboard resCollegeDashboard = ResCollegeDashboard.builder()
                .countTeachers(countTeacher)
                .countInfo(count)
                .countInfoByMonth(countInfo)
                .countDepartments(departmentCount)
                .build();
        return ApiResponse.success(resCollegeDashboard, "Success");
    }


    public ApiResponse<ResPageable> getCollegePageable(String name, int page, int size){
        Page<College> byCollegeByPage = collageRepository.findByCollegeByPage(name, PageRequest.of(page, size));

        if (byCollegeByPage.getTotalElements() == 0) {
            return ApiResponse.error("College not found");
        }

        List<ResCollage> dtoList = new ArrayList<>();

        for (College college : byCollegeByPage.getContent()) {

            List<Department> allByCollegeId = departmentRepository.findAllByCollegeIdAndActiveTrue(college.getId());

            List<String> departmentNames = allByCollegeId.stream().map(Department::getName).toList();

            ResCollage dto = collageMapper.toDTO(college, departmentNames);

            dtoList.add(dto);
        }

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalElements(byCollegeByPage.getTotalElements())
                .totalPage(byCollegeByPage.getTotalPages())
                .body(dtoList)
                .build();

        return ApiResponse.success(resPageable, "Success");
    }
}
