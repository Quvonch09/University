package univer.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
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

    @Lob
    @Column(columnDefinition = "TEXT")
    private String name;


    @ManyToOne
    private Category category;

}
