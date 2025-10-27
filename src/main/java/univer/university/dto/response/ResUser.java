package univer.university.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResUser {
    private Long id;
    private String name;
    private String lavozim;
    private String email;
    private String imgUrl;
    private String input;
    private String phoneNumber;
    private String departmentName;
}
