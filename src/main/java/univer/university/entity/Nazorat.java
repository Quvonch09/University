package univer.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import univer.university.entity.base.GeneralEntity;
import univer.university.entity.enums.MemberEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Nazorat extends GeneralEntity {
    private String researcherName;
    private String univerName;
    private String level;

    @Enumerated(EnumType.STRING)
    private MemberEnum memberEnum;
    private boolean finished;
}
