package univer.university.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRegister {

    private String fullName;

    private String phoneNumber;

    private String biography;

    private String imgUrl;

    private String fileUrl;

    private String profession;

    private String input;

    private Long lavozmId;

    @Email
    private String email;

    private int age;

    private boolean gender; // true-male, false-female

    private String password;

    private Long departmentId;

}
