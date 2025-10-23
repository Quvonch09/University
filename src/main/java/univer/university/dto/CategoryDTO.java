package univer.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import univer.university.dto.request.ReqSubCategory;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDTO {
    private Long id;
    private String name;
    private List<ReqSubCategory> subCategories;
}
