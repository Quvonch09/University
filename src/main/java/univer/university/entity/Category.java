package univer.university.entity;

import jakarta.persistence.*;
import lombok.*;
import univer.university.entity.base.BaseEntity;

import java.util.List;

@Entity(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category extends BaseEntity {
    private String name;
}
