package univer.university.entity;

import jakarta.persistence.Entity;
import lombok.*;
import univer.university.entity.base.BaseEntity;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class IlmiyDaraja extends BaseEntity {
    private String name;


}
