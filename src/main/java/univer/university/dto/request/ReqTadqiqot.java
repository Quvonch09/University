package univer.university.dto.request;

import lombok.*;
import univer.university.entity.enums.MemberEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqTadqiqot {
    private String name;
    private String description;
    private int year;
    private String fileUrl;
    private Long categoryId;
    private Long userId;
    private boolean member;
    private String univerName;
    private boolean finished;
    private MemberEnum memberEnum;
}
