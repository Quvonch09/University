package univer.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
    private String imageUrl;
    @Schema(hidden = true)
    private String role;
    @Schema(hidden = true)
    private Object object;
    @Schema(hidden = true)
    private Long categoryId;
}
