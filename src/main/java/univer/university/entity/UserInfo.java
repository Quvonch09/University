package univer.university.entity;

import jakarta.persistence.*;
import lombok.*;
import univer.university.entity.base.BaseEntity;
import univer.university.entity.enums.Level;

import java.util.List;

@Entity(name = "user_info")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserInfo extends BaseEntity {

    @OneToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private Level level;

}
