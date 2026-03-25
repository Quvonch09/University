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

    private Integer age;

    private String orcId;

    private String scopusId;

    private String scienceId;

    private String researcherId;

    private Boolean gender;

    private String imageUrl;

    private String fileUrl;

    private String profession;

    private Long lavozmId;

    private Long departmentId;

}
