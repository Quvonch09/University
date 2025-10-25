package univer.university.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import univer.university.entity.enums.MemberEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqNazorat {

    @Schema(hidden = true)
    private Long id;
    private String name;
    private String description;
    private int year;
    private String fileUrl;
    private Long userId;
    private String researcherName;
    private String univerName;
    private String level;
    private MemberEnum memberEnum;
    private boolean finished;
}
