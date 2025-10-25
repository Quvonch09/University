package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqNazorat;
import univer.university.dto.response.ResPageable;
import univer.university.entity.Nazorat;
import univer.university.entity.User;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.NazoratMapper;
import univer.university.repository.NazoratRepository;
import univer.university.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NazoratService {
    private final NazoratRepository nazoratRepository;
    private final UserRepository userRepository;
    private final NazoratMapper nazoratMapper;

    public ApiResponse<String> saveNazorat(ReqNazorat reqNazorat){
        User user = userRepository.findById(reqNazorat.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        Nazorat nazorat = Nazorat.builder()
                .univerName(reqNazorat.getUniverName())
                .level(reqNazorat.getLevel())
                .description(reqNazorat.getDescription())
                .memberEnum(reqNazorat.getMemberEnum())
                .finished(reqNazorat.isFinished())
                .researcherName(reqNazorat.getResearcherName())
                .fileUrl(reqNazorat.getFileUrl())
                .user(user)
                .year(reqNazorat.getYear())
                .name(reqNazorat.getName())
                .build();
        nazoratRepository.save(nazorat);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String> updateNazorat(Long id,ReqNazorat reqNazorat){
        User user = userRepository.findById(reqNazorat.getUserId()).orElseThrow(
                () -> new DataNotFoundException("User not found")
        );

        Nazorat nazorat = nazoratRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Nazorat not found")
        );

        nazorat.setUniverName(reqNazorat.getUniverName());
        nazorat.setLevel(reqNazorat.getLevel());
        nazorat.setDescription(reqNazorat.getDescription());
        nazorat.setMemberEnum(reqNazorat.getMemberEnum());
        nazorat.setFinished(reqNazorat.isFinished());
        nazorat.setResearcherName(reqNazorat.getResearcherName());
        nazorat.setFileUrl(reqNazorat.getFileUrl());
        nazorat.setYear(reqNazorat.getYear());
        nazorat.setName(reqNazorat.getName());
        nazorat.setUser(user);
        nazoratRepository.save(nazorat);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String> deleteNazorat(Long id){
        Nazorat nazoratNotFound = nazoratRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Nazorat not found")
        );

        nazoratRepository.delete(nazoratNotFound);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<ResPageable> getAll(int page, int size){
        Page<Nazorat> nazoratPage = nazoratRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(page, size));

        if (nazoratPage.getTotalElements() == 0){
            return ApiResponse.error("Nazorat not found");
        }

        List<ReqNazorat> list = nazoratPage.getContent().stream().map(nazoratMapper::reqNazorat).toList();

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(nazoratPage.getTotalPages())
                .totalElements(nazoratPage.getTotalElements())
                .body(list)
                .build();
        return ApiResponse.success(resPageable, "Success");
    }



    public ApiResponse<ReqNazorat> getOne(Long id){
        Nazorat nazorat = nazoratRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Nazorat not found")
        );

        return ApiResponse.success(nazoratMapper.reqNazorat(nazorat), "Success");
    }
}
