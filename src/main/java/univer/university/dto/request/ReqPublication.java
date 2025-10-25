package univer.university.dto.request;

import lombok.Getter;
import lombok.Setter;
import univer.university.entity.enums.AuthorEnum;
import univer.university.entity.enums.DegreeEnum;
import univer.university.entity.enums.PublicTypeEnum;

@Getter
@Setter
public class ReqPublication {

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
