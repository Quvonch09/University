package univer.university.entity;

import jakarta.persistence.*;
import lombok.*;
import univer.university.entity.base.BaseEntity;

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

    @OneToMany
    private List<Info> info;

}
