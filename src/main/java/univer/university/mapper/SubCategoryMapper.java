package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.SubCategoryDTO;
import univer.university.entity.Category;
import univer.university.entity.SubCategory;
import univer.university.exception.DataNotFoundException;
import univer.university.repository.CategoryRepository;

@Component
public class SubCategoryMapper {

    private CategoryRepository categoryRepository;

    public SubCategoryDTO subCategoryDTO(SubCategory subCategory) {
        return SubCategoryDTO.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .categoryId(subCategory.getCategory().getId())
                .categoryName(subCategory.getCategory().getName())
                .build();
    }
}
