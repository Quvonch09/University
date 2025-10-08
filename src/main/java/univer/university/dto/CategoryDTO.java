package univer.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import univer.university.entity.Category;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDTO {
    @Schema(hidden = true)
    private Long id;
    private String name;
    private Category subCategory;
}
