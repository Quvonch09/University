package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.SubCategoryDTO;
import univer.university.dto.request.ReqSubCategory;
import univer.university.entity.Category;
import univer.university.entity.SubCategory;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.SubCategoryMapper;
import univer.university.repository.CategoryRepository;
import univer.university.repository.SubCategoryRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    public ApiResponse<String> addSubCategory(ReqSubCategory req) {
        boolean b = subCategoryRepository.existsByName(req.getName());
        if (b){
            return ApiResponse.error("this subcategory already exists");
        }
        Category category = categoryRepository.findById(req.getCategoryId()).orElseThrow(
                () -> new DataNotFoundException("this category does not exist"));

        SubCategory subCategory = new SubCategory();
        subCategory.setName(req.getName());
        subCategory.setCategory(category);
        subCategoryRepository.save(subCategory);

        return ApiResponse.success(null,"success");
    }

    public ApiResponse<List<SubCategoryDTO>> getAllList(){
        List<SubCategory> subCategories = subCategoryRepository.findAll();

        List<SubCategoryDTO> subCategoryDTOS = subCategories.stream().map(subCategoryMapper::subCategoryDTO).toList();
        return ApiResponse.success(subCategoryDTOS,"success");
    }

    public ApiResponse<SubCategoryDTO> getSubCategoryById(Long id) {
        SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("this subcategory does not exist"));
        return ApiResponse.success(subCategoryMapper.subCategoryDTO(subCategory),"success");

    }




}
