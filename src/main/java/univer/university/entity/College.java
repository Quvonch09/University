package univer.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import univer.university.entity.base.BaseEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class College extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String imgUrl;

    private boolean active;
}
