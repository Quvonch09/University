package univer.university.dto.request;

import lombok.Getter;
import lombok.Setter;
import univer.university.entity.enums.Level;

@Getter
@Setter
public class ReqUserInfo {
    private Long userId;
    private Level level;
}
