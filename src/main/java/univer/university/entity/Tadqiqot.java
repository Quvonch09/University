package univer.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import univer.university.entity.base.GeneralEntity;
import univer.university.entity.enums.MemberEnum;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tadqiqot extends GeneralEntity {

    private boolean member; //1-ha, 0-yuq

    @Enumerated(EnumType.STRING)
    private MemberEnum memberEnum;

    private String univerName;

    private boolean finished;
}
