package univer.university.entity;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import univer.university.configuration.GenericJsonConverter;
import univer.university.entity.base.BaseEntity;

@Entity(name = "information")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Info extends BaseEntity {

    @Convert(converter = GenericJsonConverter.class)
    private Object object;

    @ManyToOne
    private Category category;

}
