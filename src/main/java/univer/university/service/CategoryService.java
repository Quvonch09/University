package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.CategoryDTO;
import univer.university.dto.request.ReqCategory;
import univer.university.entity.Category;
import univer.university.exception.DataNotFoundException;
import univer.university.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public ApiResponse<String> addCategory(ReqCategory req) {

        boolean b = categoryRepository.existsByName(req.getName());
        if (b) {
            return ApiResponse.error("this category already exists");
        }

        Category subCategory = null;
        if (req.getSubCategory() != null) {
            subCategory = Category.builder()
                    .name(req.getSubCategory().getName())
                    .build();
            categoryRepository.save(subCategory);
        }

        Category newCategory = Category.builder()
                .name(req.getName())
                .subCategory(subCategory)
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
            categoryDTO.setSubCategory(category.getSubCategory());
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
                .subCategory(category.getSubCategory())
                .build();
        return ApiResponse.success(categoryDTO,"success");

    }



}
