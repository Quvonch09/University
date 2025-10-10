package univer.university.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResDepartment {
    private Long id;
    private String name;
    private String imgUrl;
}
