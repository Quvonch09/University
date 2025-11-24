package univer.university.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResUser {
    private Long id;
    private String fullName;
    private String lavozim;
    private String email;
    private int age;
    private boolean gender;
    private String profession;
    private String imgUrl;
    private String input;
    private String phoneNumber;
    private String departmentName;
}
