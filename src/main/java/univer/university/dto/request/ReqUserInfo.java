package univer.university.dto.request;

import lombok.Getter;
import lombok.Setter;
import univer.university.entity.enums.AcademicTitle;

@Getter
@Setter
public class ReqUserInfo {
    private Long userId;
    private AcademicTitle academicTitle;
}
