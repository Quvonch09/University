package univer.university.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqIlmiyDaraja {

    @NotBlank(message = "Name bo'sh bo'lmasligi kerak")
    private String name;
}