package univer.university.entity;

import jakarta.persistence.Column;
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
public class Department extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private College college;

    private String imgUrl;

    private boolean active;
}
