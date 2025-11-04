package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.UserDTO;
import univer.university.dto.response.ResPageable;
import univer.university.dto.response.ResUser;
import univer.university.entity.User;

@Component
public class UserMapper {
    public UserDTO userDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .phone(user.getPhone())
                .imageUrl(user.getImgUrl())
                .fullName(user.getFullName())
                .build();
    }



    public ResUser resUser(User user){
        return ResUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getFullName())
                .input(user.getInput())
                .departmentName(user.getDepartment().getName())
                .imgUrl(user.getImgUrl())
                .lavozim(user.getLavozm().getName())
                .phoneNumber(user.getPhone())
                .build();
    }



    public UserDTO userToDTO(User user,
                             ResPageable qualification,
                             ResPageable research,
                             ResPageable award,
                             ResPageable consultation,
                             ResPageable nazorat,
                             ResPageable publication) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .phone(user.getPhone())
                .imageUrl(user.getImgUrl())
                .fullName(user.getFullName())
                .input(user.getInput())
                .departmentName(user.getDepartment().getName())
                .award(award)
                .consultation(consultation)
                .nazorat(nazorat)
                .publication(publication)
                .research(research)
                .lavozimName(user.getLavozm().getName())
                .biography(user.getBiography())
                .fileUrl(user.getFileUrl())
                .profession(user.getProfession())
                .qualification(qualification)
                .build();
    }

}
