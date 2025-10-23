package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.CategoryDTO;
import univer.university.dto.request.ReqCategory;
import univer.university.dto.request.ReqSubCategory;
import univer.university.entity.Category;
import univer.university.entity.SubCategory;
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

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public ApiResponse<String> addCategory(ReqCategory req) {

        boolean b = categoryRepository.existsByName(req.getName());
        if (b) {
            return ApiResponse.error("this category already exists");
        }


        Category newCategory = Category.builder()
                .name(req.getName())
                .build();
        Category save = categoryRepository.save(newCategory);

        for (String subCategoryName : req.getSubCategoryNames()) {
            SubCategory subCategory = SubCategory.builder()
                    .name(subCategoryName)
                    .category(save)
                    .build();
            subCategoryRepository.save(subCategory);
        }

        return ApiResponse.success(null,"success");
    }


    public ApiResponse<List<CategoryDTO>> getAllCategories() {
        List<Category> all = categoryRepository.findAll();
        if (all.isEmpty()) {
            return ApiResponse.error("category not found");
        }
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : all) {
            List<ReqSubCategory> subCategories = subCategoryRepository.findAllByCategoryId(category.getId()).stream().map(this::reqSubCategory).toList();

            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setSubCategories(subCategories);
            categoryDTOList.add(categoryDTO);
        }
        return ApiResponse.success(categoryDTOList,"success");

    }

    public ApiResponse<CategoryDTO> getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("bunday id li category topilmadi"));

        List<ReqSubCategory> subCategories = subCategoryRepository.findAllByCategoryId(category.getId()).stream().map(this::reqSubCategory).toList();

        CategoryDTO categoryDTO = CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .subCategories(subCategories)
                .build();
        return ApiResponse.success(categoryDTO,"success");

    }



    public ApiResponse<String> updateCategory(CategoryDTO categoryDTO){
        Category category = categoryRepository.findById(categoryDTO.getId()).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );

        category.setName(categoryDTO.getName());
        categoryRepository.save(category);

        for (ReqSubCategory subCategory : categoryDTO.getSubCategories()) {
            SubCategory subCategory1 = subCategoryRepository.findById(subCategory.getId()).orElseThrow(
                    () -> new DataNotFoundException("subCategory not found")
            );

            subCategory1.setName(subCategory.getName());
            subCategoryRepository.save(subCategory1);
        }

        return ApiResponse.success(null,"success");
    }




    public ApiResponse<String> deleteCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Category not found")
        );

        List<SubCategory> subCategories = subCategoryRepository.findAllByCategoryId(category.getId());
        subCategoryRepository.deleteAll(subCategories);

        categoryRepository.delete(category);

        return ApiResponse.success(null,"success");
    }


    private ReqSubCategory reqSubCategory(SubCategory subCategory) {
        return ReqSubCategory.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .build();
    }


}
