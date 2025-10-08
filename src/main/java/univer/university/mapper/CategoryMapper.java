package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.CategoryDTO;
import univer.university.entity.Category;

@Component
public class CategoryMapper {
    public CategoryDTO categoryDTO(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .subCategory(category.getSubCategory())
                .build();
    }
}
