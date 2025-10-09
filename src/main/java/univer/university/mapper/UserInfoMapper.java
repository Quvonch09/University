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
                .level(userInfo.getLevel())
                .build();

    }

}
