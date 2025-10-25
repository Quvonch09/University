package univer.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;
import univer.university.entity.base.GeneralEntity;
import univer.university.entity.enums.AuthorEnum;
import univer.university.entity.enums.DegreeEnum;
import univer.university.entity.enums.PublicTypeEnum;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "publication")
public class Publication extends GeneralEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PublicTypeEnum type;
    @Enumerated(EnumType.STRING)
    private AuthorEnum author;
    @Enumerated(EnumType.STRING)
    private DegreeEnum degree;
    private String volume;
    private String institution;
    private boolean popular;

}
