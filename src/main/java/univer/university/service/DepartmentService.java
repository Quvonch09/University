package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.DepartmentDTO;
import univer.university.dto.request.ReqDepartment;
import univer.university.entity.College;
import univer.university.entity.Department;
import univer.university.entity.enums.AcademicTitle;
import univer.university.entity.enums.Level;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.DepartmentMapper;
import univer.university.repository.CollageRepository;
import univer.university.repository.DepartmentRepository;
import univer.university.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final CollageRepository collageRepository;
    private final DepartmentMapper departmentMapper;
    private final UserRepository userRepository;


    public ApiResponse<String> addDepartment(ReqDepartment reqDepartment){
        College college = collageRepository.findById(reqDepartment.getCollegeId()).orElseThrow(
                () -> new DataNotFoundException("College not found")
        );

        Department department = Department.builder()
                .name(reqDepartment.getName())
                .college(college)
                .imgUrl(reqDepartment.getImgUrl())
                .active(true)
                .build();
        departmentRepository.save(department);
        return ApiResponse.success(null, "department added successfully");
    }


    public ApiResponse<String> updateDepartment(Long id, ReqDepartment reqDepartment){
        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Department not found")
        );

        College college = collageRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Collage not found")
        );

        department.setName(reqDepartment.getName());
        department.setImgUrl(reqDepartment.getImgUrl());
        department.setCollege(college);
        departmentRepository.save(department);
        return ApiResponse.success(null, "department updated successfully");
    }


    public ApiResponse<String> deleteDepartment(Long id){
        Department department = departmentRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Department not found")
        );

        department.setActive(false);
        departmentRepository.save(department);
        return ApiResponse.success(null, "department deleted successfully");
    }


    public ApiResponse<List<ReqDepartment>> getDepartmentByCollege(Long collegeId){
        College college = collageRepository.findByIdAndActiveTrue(collegeId).orElseThrow(
                () -> new DataNotFoundException("College not found")
        );

        List<Department> departments = departmentRepository.findAllByCollegeIdAndActiveTrue(college.getId());
        List<ReqDepartment> list = departments.stream().map(departmentMapper::toDTO).toList();
        return ApiResponse.success(list, "department list successfully");
    }


    public ApiResponse<DepartmentDTO> getOneDepartment(Long id){
        Department department = departmentRepository.findByIdAndActiveTrue(id).orElseThrow(
                () -> new DataNotFoundException("Department not found")
        );

        long countAllUsers = userRepository.countByDepartment(department.getId());
        long countPHD = userRepository.countLevelByDepartment(department.getId(), Level.FAN_NOMZODI_PhD.name());
        long countDSC = userRepository.countLevelByDepartment(department.getId(), Level.FAN_DOKTORI_DSc.name());
        long countProfessor = userRepository.countAcademicByDepartment(department.getId(), AcademicTitle.PROFESSOR.name());
        long countDotsent = userRepository.countAcademicByDepartment(department.getId(), AcademicTitle.DOTSENT.name());
        long countNull = userRepository.countByDepartmentNone(department.getId());


        DepartmentDTO departmentDTO = DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .countUsers(countAllUsers)
                .countPHD(countPHD)
                .countDSC(countDSC)
                .countProfessor(countProfessor)
                .countDotsent(countDotsent)
                .countNull(countNull)
                .build();
        return ApiResponse.success(departmentDTO, "department get successfully");
    }
}
