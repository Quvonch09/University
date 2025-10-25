package univer.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import univer.university.entity.base.GeneralEntity;
import univer.university.entity.enums.FinishedEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity(name = "consultation")
public class Consultation extends GeneralEntity {

    private boolean member;

    @Enumerated(EnumType.STRING)
    private FinishedEnum finishedEnum;

    private boolean isLeader;

}
