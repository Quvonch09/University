package univer.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import univer.university.entity.base.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SubCategory extends BaseEntity {
    private String name;

    @ManyToOne
    private Category category;
}
