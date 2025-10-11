package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.CategoryDTO;
import univer.university.dto.request.ReqCategory;
import univer.university.entity.Category;
import univer.university.entity.UserInfo;
import univer.university.exception.DataNotFoundException;
import univer.university.repository.CategoryRepository;
import univer.university.repository.SubCategoryRepository;
import univer.university.repository.UserInfoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final UserInfoRepository userInfoRepository;
    private final CategoryRepository categoryRepository;

    public ApiResponse<String> addCategory(ReqCategory req) {

        boolean b = categoryRepository.existsByName(req.getName());
        if (b) {
            return ApiResponse.error("this category already exists");
        }
        UserInfo userInfo = userInfoRepository.findById(req.getUserInfoId()).orElseThrow(() -> new DataNotFoundException("userInfo not found"));

        Category newCategory = Category.builder()
                .name(req.getName())
                .userInfo(userInfo)
                .build();
        categoryRepository.save(newCategory);

        return ApiResponse.success(null,"success");
    }


    public ApiResponse<List<CategoryDTO>> getAllCategories() {
        List<Category> all = categoryRepository.findAll();
        if (all.isEmpty()) {
            return ApiResponse.error(null);
        }
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : all) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setUserInfoId(category.getUserInfo().getId());
            categoryDTOList.add(categoryDTO);
        }
        return ApiResponse.success(categoryDTOList,"success");

    }

    public ApiResponse<CategoryDTO> getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("bunday id li category topilmadi"));
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .userInfoId(category.getUserInfo().getId())
                .build();
        return ApiResponse.success(categoryDTO,"success");

    }



}
