package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.SubCategoryDTO;
import univer.university.entity.SubCategory;

@Component
public class SubCategoryMapper {
    public SubCategoryDTO subCategoryDTO(SubCategory subCategory) {
        return SubCategoryDTO.builder()
                .id(subCategory.getId())
                .name(subCategory.getName())
                .categoryId(subCategory.getCategory().getId())
                .build();
    }
}
