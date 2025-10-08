package univer.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import univer.university.entity.base.BaseEntity;

@Entity(name = "info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Info extends BaseEntity {

    private Object name;

    @ManyToOne
    private Category category;

}
