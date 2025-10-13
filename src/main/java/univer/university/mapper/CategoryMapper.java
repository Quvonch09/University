package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.CategoryDTO;
import univer.university.entity.Category;

@Component
public class CategoryMapper {
    public CategoryDTO categoryDTO(Category category) {
        return CategoryDTO.builder()
                .name(category.getName())
                .userInfoId(category.getUserInfo() != null ? category.getUserInfo().getId() : null)
                .parentCategoryId(category.getParent() != null ? category.getParent().getId() : null)
                .build();
    }
}
