package univer.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubCategoryDTO {
    @Schema(hidden = true)
    private Long id;
    private String name;
    @Schema(hidden = true)
    private Long categoryId;
}
