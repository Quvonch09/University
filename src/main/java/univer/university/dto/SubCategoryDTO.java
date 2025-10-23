package univer.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategoryDTO {
    @Schema(hidden = true)
    private Long id;

    private Long categoryId;

    private String subCategoryName;
}
