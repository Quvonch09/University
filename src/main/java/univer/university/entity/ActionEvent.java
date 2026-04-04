package univer.university.entity;

import jakarta.persistence.*;
import lombok.*;
import univer.university.entity.enums.ActionType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ActionEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String description;

    private Long userId;

    private String userRole;

    private String entityName;

    private Long entityId;

    private LocalDateTime createdAt;
}
