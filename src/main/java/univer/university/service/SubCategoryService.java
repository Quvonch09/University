package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import univer.university.dto.ApiResponse;
import univer.university.dto.SubCategoryDTO;
import univer.university.dto.request.ReqSubCategory;
import univer.university.entity.Category;
import univer.university.entity.SubCategory;
import univer.university.exception.DataNotFoundException;
import univer.university.repository.CategoryRepository;
import univer.university.repository.SubCategoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;


    public ApiResponse<String> saveSubCategory(ReqSubCategory reqSubCategory){
        Category categoryNotFound = categoryRepository.findById(reqSubCategory.getCategoryId()).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );

        for (String subCategory : reqSubCategory.getSubCategories()) {
            SubCategory subCategory1 = SubCategory.builder()
                    .category(categoryNotFound)
                    .name(subCategory)
                    .build();
            subCategoryRepository.save(subCategory1);
        }

        return ApiResponse.success(null, "Success");
    }


    public ApiResponse<String> updateSubCategory(Long subCategoryId, SubCategoryDTO subCategoryDTO){
        Category categoryNotFound = categoryRepository.findById(subCategoryDTO.getCategoryId()).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );

        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
                () -> new DataNotFoundException("SubCategory not found")
        );

        subCategory.setName(subCategory.getName());
        subCategory.setCategory(categoryNotFound);
        subCategoryRepository.save(subCategory);

        return ApiResponse.success(null, "Success");
    }


    public ApiResponse<String> deleteSubCategory(Long subCategoryId){
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId).orElseThrow(
                () -> new DataNotFoundException("SubCategory not found")
        );

        subCategoryRepository.delete(subCategory);
        return ApiResponse.success(null, "Success");
    }


    public ApiResponse<List<SubCategoryDTO>> getAllSubCategories(){
        List<SubCategoryDTO> list = subCategoryRepository.findAll().stream().map(this::subCategoryDTO).toList();
        return ApiResponse.success(list, "Success");
    }



    public ApiResponse<List<SubCategoryDTO>> getSubCategoryByCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );

        List<SubCategoryDTO> list = subCategoryRepository.findAllByCategoryId(category.getId()).stream().map(this::subCategoryDTO).toList();
        return ApiResponse.success(list, "Success");
    }


    private SubCategoryDTO subCategoryDTO(SubCategory subCategory){
        return SubCategoryDTO.builder()
                .id(subCategory.getId())
                .subCategoryName(subCategory.getName())
                .categoryId(subCategory.getCategory().getId())
                .build();
    }

}
