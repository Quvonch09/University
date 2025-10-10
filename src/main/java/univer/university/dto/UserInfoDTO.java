package univer.university.dto;

import lombok.*;
import univer.university.entity.enums.AcademicTitle;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDTO {
    private Long id;
    private Long userId;
    private String academicTitle;
    private String level;
}
