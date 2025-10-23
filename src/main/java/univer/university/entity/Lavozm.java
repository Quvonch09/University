package univer.university.entity;


import jakarta.persistence.Entity;
import lombok.*;
import univer.university.entity.base.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Lavozm extends BaseEntity {
    private String name;
}
