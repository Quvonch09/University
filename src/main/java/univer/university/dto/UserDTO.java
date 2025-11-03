package univer.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import univer.university.dto.response.ResPageable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @Schema(hidden = true)
    private Long id;

    private String fullName;

    private String phone;

    private String email;

    private String biography;

    private String input;

    private String imageUrl;

    @Schema(hidden = true)
    private String role;

    private String fileUrl;

    private String profession;

    @Schema(hidden = true)
    private String lavozimName;

    @Schema(hidden = true)
    private String departmentName;

    @Schema(hidden = true)
    private ResPageable qualification;

    @Schema(hidden = true)
    private ResPageable research;

    @Schema(hidden = true)
    private ResPageable award;

    @Schema(hidden = true)
    private ResPageable consultation;

    @Schema(hidden = true)
    private ResPageable nazorat;

    @Schema(hidden = true)
    private ResPageable publication;
}
