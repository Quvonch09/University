package univer.university.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfoDTO {
    private Long id;
    private Object name;
    private Long categoryId;
}
