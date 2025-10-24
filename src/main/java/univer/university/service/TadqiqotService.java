package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqTadqiqot;
import univer.university.entity.Tadqiqot;
import univer.university.entity.User;
import univer.university.exception.DataNotFoundException;
import univer.university.repository.TadqiqotRepository;
import univer.university.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class TadqiqotService {
    private final TadqiqotRepository tadqiqotRepository;
    private final UserRepository userRepository;

    public ApiResponse<String> saveTadqiqot(ReqTadqiqot reqTadqiqot) {


        User user = userRepository.findById(reqTadqiqot.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        Tadqiqot tadqiqot = Tadqiqot.builder()
                .name(reqTadqiqot.getName())
                .fileUrl(reqTadqiqot.getFileUrl())
                .finished(reqTadqiqot.isFinished())
                .description(reqTadqiqot.getDescription())
                .memberEnum(reqTadqiqot.getMemberEnum())
                .univerName(reqTadqiqot.getUniverName())
                .member(reqTadqiqot.isMember())
                .user(user)
                .year(reqTadqiqot.getYear())
                .build();
        tadqiqotRepository.save(tadqiqot);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String> updateTadqiqot(Long id, ReqTadqiqot reqTadqiqot){
        User user = userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        Tadqiqot tadqiqot = tadqiqotRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Tadqiqot not found")
        );

        tadqiqot.setName(reqTadqiqot.getName());
        tadqiqot.setFileUrl(reqTadqiqot.getFileUrl());
        tadqiqot.setFinished(reqTadqiqot.isFinished());
        tadqiqot.setDescription(reqTadqiqot.getDescription());
        tadqiqot.setMemberEnum(reqTadqiqot.getMemberEnum());
        tadqiqot.setUniverName(reqTadqiqot.getUniverName());
        tadqiqot.setMember(reqTadqiqot.isMember());
        tadqiqot.setYear(reqTadqiqot.getYear());
        tadqiqot.setUser(user);
        tadqiqotRepository.save(tadqiqot);
        return ApiResponse.success(null, "Success");
    }
}
