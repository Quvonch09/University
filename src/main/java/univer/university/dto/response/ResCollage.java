package univer.university.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResCollage {
    private Long id;
    private String name;
    private String imgUrl;
    private int departmentCount;
    private List<String> departmentNames;
}
