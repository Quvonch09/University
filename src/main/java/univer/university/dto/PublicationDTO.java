package univer.university.dto;

import lombok.*;
import univer.university.entity.enums.AuthorEnum;
import univer.university.entity.enums.DegreeEnum;
import univer.university.entity.enums.PublicTypeEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PublicationDTO {
    private Long id;
    private Long userId;
    private String name;
    private String description;
    private Integer year;
    private String fileUrl;
    private PublicTypeEnum type;
    private AuthorEnum author;
    private DegreeEnum degree;
    private String volume;
    private String institution;
    private boolean popular;
}
