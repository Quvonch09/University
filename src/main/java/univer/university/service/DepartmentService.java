package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqDepartment;
import univer.university.entity.College;
import univer.university.entity.Department;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.DepartmentMapper;
import univer.university.repository.CollageRepository;
import univer.university.repository.DepartmentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final CollageRepository collageRepository;
    private final DepartmentMapper departmentMapper;


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

        departmentRepository.delete(department);
        return ApiResponse.success(null, "department deleted successfully");
    }


    public ApiResponse<List<ReqDepartment>> getDepartmentByCollege(Long collegeId){
        College college = collageRepository.findById(collegeId).orElseThrow(
                () -> new DataNotFoundException("College not found")
        );

        List<Department> departments = departmentRepository.findAllByCollegeId(college.getId());
        List<ReqDepartment> list = departments.stream().map(departmentMapper::toDTO).toList();
        return ApiResponse.success(list, "department list successfully");
    }
}
