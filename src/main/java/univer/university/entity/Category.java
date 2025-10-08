package univer.university.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import univer.university.entity.base.BaseEntity;

@Entity(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category extends BaseEntity {

    private String name;

    @ManyToOne
    private Category subCategory;
}
