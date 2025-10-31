package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqAward;
import univer.university.dto.response.ResPageable;
import univer.university.entity.Award;
import univer.university.entity.User;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.AwardMapper;
import univer.university.repository.AwardRepository;
import univer.university.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AwardService {
    private final AwardRepository awardRepository;
    private final UserRepository userRepository;
    private final AwardMapper awardMapper;


    public ApiResponse<String> saveAward(ReqAward reqAward){
        User user = userRepository.findById(reqAward.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        Award award = Award.builder()
                .user(user)
                .year(reqAward.getYear())
                .input(reqAward.getInput())
                .description(reqAward.getDescription())
                .memberEnum(reqAward.getMemberEnum())
                .fileUrl(reqAward.getFileUrl())
                .name(reqAward.getName())
                .build();
        awardRepository.save(award);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String> updateAward(Long id,ReqAward reqAward){
        Award award = awardRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Award not found")
        );

        User user = userRepository.findById(reqAward.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        award.setYear(reqAward.getYear());
        award.setInput(reqAward.getInput());
        award.setDescription(reqAward.getDescription());
        award.setMemberEnum(reqAward.getMemberEnum());
        award.setFileUrl(reqAward.getFileUrl());
        award.setName(reqAward.getName());
        award.setUser(user);
        awardRepository.save(award);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String>  deleteAward(Long id){
        Award award = awardRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Award not found")
        );

        awardRepository.delete(award);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<ResPageable> getAll(int page, int size){
        Page<Award> awardPage = awardRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));
        return check(awardPage, page, size);
    }



    public ApiResponse<ReqAward> getAwardById(Long id){
        Award award = awardRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Award not found")
        );
        return ApiResponse.success(awardMapper.reqAward(award), "Success");
    }



    public ApiResponse<ResPageable> getByUserId(Long id, int page, int size){
        User user = userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        Page<Award> awardPage = awardRepository.findByUser(user.getId(), PageRequest.of(page, size));
        return check(awardPage, page, size);
    }



    public ApiResponse<ResPageable> check(Page<Award> awardPage, int page, int size){
//        if (awardPage.getTotalElements() == 0){
//            return ApiResponse.error("Award not found");
//        }

        List<ReqAward> list = awardPage.getContent().stream().map(awardMapper::reqAward).toList();

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(awardPage.getTotalPages())
                .totalElements(awardPage.getTotalElements())
                .body(list)
                .build();
        return ApiResponse.success(resPageable, "Success");
    }
}
