package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.UserInfoDTO;
import univer.university.entity.UserInfo;

@Component
public class UserInfoMapper {

    public UserInfoDTO userInfoDTO(UserInfo userInfo) {
        return UserInfoDTO.builder()
                .id(userInfo.getId())
                .userId(userInfo.getUser().getId())
                .categoryId(userInfo.getCategory().getId())
                .academicTitle(userInfo.getAcademicTitle().name())
                .level(userInfo.getLevel().name())
                .build();

    }

}
