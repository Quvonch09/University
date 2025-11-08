package univer.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import univer.university.entity.base.GeneralEntity;
import univer.university.entity.enums.AwardEnum;
import univer.university.entity.enums.MemberEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Award extends GeneralEntity {
    private AwardEnum awardEnum;
    @Enumerated(EnumType.STRING)
    private MemberEnum memberEnum;
}
