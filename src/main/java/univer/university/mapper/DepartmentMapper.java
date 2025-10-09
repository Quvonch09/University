package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.request.ReqDepartment;
import univer.university.entity.Department;

@Component
public class DepartmentMapper {
    public ReqDepartment toDTO(Department department) {
        return ReqDepartment.builder()
                .id(department.getId())
                .name(department.getName())
                .imgUrl(department.getImgUrl())
                .collegeId(department.getCollege().getId())
                .collegeName(department.getCollege().getName())
                .build();
    }
}
