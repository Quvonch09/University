package univer.university.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import univer.university.entity.enums.AwardEnum;
import univer.university.entity.enums.MemberEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqAward {
    @Schema(hidden = true)
    private Long id;
    private String name;
    private String description;
    private int year;
    private String fileUrl;
    private Long userId;
    private AwardEnum awardEnum;
    private MemberEnum memberEnum;
}
