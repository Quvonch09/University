package univer.university.dto;

import lombok.*;
import univer.university.entity.enums.FinishedEnum;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConsultationDTO {

    private Long id;

    private String name;

    private String description;

    private int year;

    private String fileUrl;

    private Long userId;

    private boolean member;

    private FinishedEnum finishedEnum;

    private boolean isLeader;
}
