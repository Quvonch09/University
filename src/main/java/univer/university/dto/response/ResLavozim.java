package univer.university.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResLavozim {
    @Schema(hidden = true)
    private  Long id;

    private  String name;
}
