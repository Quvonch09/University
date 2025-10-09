package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.response.ResCollage;
import univer.university.entity.College;

import java.util.List;

@Component
public class CollageMapper {
    public ResCollage toDTO(College college, List<String> departmentNames){
        return ResCollage.builder()
                .id(college.getId())
                .name(college.getName())
                .departmentNames(departmentNames)
                .departmentCount(departmentNames != null ? departmentNames.size() : 0)
                .imgUrl(college.getImgUrl())
                .build();
    }
}
