package univer.university.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRegister {
    private String fullName;
    private String phoneNumber;
    private String email;
    private int age;
    private boolean gender; // true-male, false-female
    private String password;
    private Long departmentId;

}
