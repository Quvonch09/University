package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.UserInfoDTO;
import univer.university.dto.request.ReqUserInfo;
import univer.university.entity.User;
import univer.university.entity.UserInfo;
import univer.university.exception.BadRequestException;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.UserInfoMapper;
import univer.university.repository.UserInfoRepository;
import univer.university.repository.UserRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserRepository userRepository;

    private final UserInfoMapper userInfoMapper;

    private final UserInfoRepository userInfoRepository;

    public ApiResponse<String> addUserInfo(ReqUserInfo req){

        User user = userRepository.findById(req.getUserId()).orElseThrow(() -> new DataNotFoundException("User not found"));
        UserInfo userInfo = UserInfo.builder()
                .user(user)
                .academicTitle(req.getAcademicTitle())
                .level(req.getLevel())
                .build();
        userInfoRepository.save(userInfo);
        return ApiResponse.success(null,"success");
    }

    public ApiResponse<List<UserInfoDTO>> getAllList(){
        List<UserInfo> all = userInfoRepository.findAll();
        List<UserInfoDTO> userInfoDTOS = all.stream().map(userInfoMapper::userInfoDTO).toList();
        return ApiResponse.success(userInfoDTOS,"success");
    }

    public ApiResponse<UserInfoDTO> getUserInfoById(Long userInfoId){
        UserInfo userInfo = userInfoRepository.findById(userInfoId).orElseThrow(() -> new DataNotFoundException("User not found"));
        UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                .id(userInfo.getId())
                .userId(userInfo.getUser().getId())
                .academicTitle(userInfo.getAcademicTitle().name())
                .level(userInfo.getLevel().name())
                .build();
        return ApiResponse.success(userInfoDTO,"success");
    }


}
