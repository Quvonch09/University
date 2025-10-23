package univer.university.entity;

import jakarta.persistence.*;
import lombok.*;
import univer.university.entity.base.BaseEntity;
import univer.university.entity.enums.AcademicTitle;
import univer.university.entity.enums.Level;

@Entity(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserInfo extends BaseEntity {

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @Enumerated(EnumType.STRING)
    private AcademicTitle academicTitle;

    @Enumerated(EnumType.STRING)
    private Level level;

}
