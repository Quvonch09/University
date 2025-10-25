package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqTadqiqot;
import univer.university.dto.response.ResPageable;
import univer.university.entity.Tadqiqot;
import univer.university.entity.User;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.TadqiqotMapper;
import univer.university.repository.TadqiqotRepository;
import univer.university.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TadqiqotService {
    private final TadqiqotRepository tadqiqotRepository;
    private final UserRepository userRepository;
    private final TadqiqotMapper tadqiqotMapper;

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
        User user = userRepository.findById(reqTadqiqot.getUserId()).orElseThrow(
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


    public ApiResponse<String> deleteTadqiqot(Long id){
        Tadqiqot tadqiqot = tadqiqotRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Tadqiqot not found")
        );

        tadqiqotRepository.delete(tadqiqot);
        return ApiResponse.success(null, "Success");
    }





    public ApiResponse<ResPageable> getAll(int page, int size){
        Page<Tadqiqot> tadqiqotPage = tadqiqotRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));

        if (tadqiqotPage.getTotalElements() == 0){
            return ApiResponse.error("Tadqiqot not found");
        }

        List<ReqTadqiqot> reqTadqiqots = tadqiqotPage.getContent().stream().map(tadqiqotMapper::reqTadqiqot).toList();

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(tadqiqotPage.getTotalPages())
                .totalElements(tadqiqotPage.getTotalElements())
                .body(reqTadqiqots)
                .build();

        return ApiResponse.success( resPageable, "Success");

    }


    public ApiResponse<ReqTadqiqot> getOneTadqiqot(Long id){
        Tadqiqot tadqiqot = tadqiqotRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Tadqiqot not found")
        );

        ReqTadqiqot reqTadqiqot = tadqiqotMapper.reqTadqiqot(tadqiqot);
        return ApiResponse.success(reqTadqiqot, "Success");
    }
}
