package univer.university.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqDepartment {
    @Schema(hidden = true)
    private Long id;
    private String name;
    private String imgUrl;
    private Long collegeId;
    @Schema(hidden = true)
    private String collegeName;
}
