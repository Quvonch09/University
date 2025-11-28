package univer.university.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReqUserDTO {
    private Long id;

    private String fullName;

    private String phoneNumber;

    private String email;

    private String biography;

    private String input;

    private int age;

    private boolean gender;

    private String imageUrl;

    private String fileUrl;

    private String profession;

    private Long lavozmId;

    private Long departmentId;

}
