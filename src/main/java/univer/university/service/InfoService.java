package univer.university.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.InfoDTO;
import univer.university.dto.request.ReqInfo;
import univer.university.dto.request.ReqPage;
import univer.university.dto.request.ReqUpdateInfo;
import univer.university.dto.response.ResPageable;
import univer.university.entity.Category;
import univer.university.entity.Info;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.InfoMapper;
import univer.university.repository.CategoryRepository;
import univer.university.repository.InfoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InfoService {
    private final InfoRepository infoRepository;

    private final InfoMapper infoMapper;

    private final CategoryRepository categoryRepository;

    public ApiResponse<String> addInfo(ReqInfo req) {
        Category category = categoryRepository.findById(req.getCategoryId()).orElseThrow(() -> new DataNotFoundException("Category not found"));

        Info info = Info.builder()
                .object(req.getObject())
                .category(category)
                .build();
        infoRepository.save(info);
        return ApiResponse.success(null,"success");

    }

    public ApiResponse<ResPageable> getPageInfo(ReqPage req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<Info> infos = infoRepository.findAll(pageable);
        if (infos.isEmpty()){
            return ApiResponse.error("info is empty");
        }

        List<InfoDTO> infoDTOS = infos.stream().map(infoMapper::infoDTO).toList();

        ResPageable resPageable = ResPageable.builder()
                .page(req.getPage())
                .size(req.getSize())
                .totalPage(infos.getTotalPages())
                .totalElements(infos.getTotalElements())
                .body(infoDTOS)
                .build();
        return ApiResponse.success(resPageable,"success");

    }

    public ApiResponse<String> updateInfo(ReqUpdateInfo req){

        Info info = infoRepository.findById(req.getId()).orElseThrow(() -> new DataNotFoundException("Info not found"));
        Category category = categoryRepository.findById(req.getCategoryId()).orElseThrow(() -> new DataNotFoundException("Category not found"));

        info.setObject(req.getObjectName());
        info.setCategory(category);
        infoRepository.save(info);
        return ApiResponse.success(null,"success");

    }

    public ApiResponse<InfoDTO> getInfoById(Long id){
        Info info = infoRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Info not found"));
        InfoDTO infoDTO = infoMapper.infoDTO(info);
        return ApiResponse.success(infoDTO,"success");
    }

}
