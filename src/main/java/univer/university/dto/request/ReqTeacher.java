package univer.university.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqTeacher {

    private Long id;

    private String fullName;

    private String phoneNumber;

    private String imgUrl;

    private String fileUrl;

    private Long lavozmId;

    private boolean gender; // true-male, false-female

    private String password;

    private Long departmentId;

}
