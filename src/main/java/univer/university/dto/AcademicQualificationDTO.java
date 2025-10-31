package univer.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademicQualificationDTO {

    @Schema(hidden = true)
    private Long id;

    private String degreeLevel;

    private String specialization;

    private String description;

    private int startYear;

    private int endYear;

    private String institutionName;

    private Long userId;
}
