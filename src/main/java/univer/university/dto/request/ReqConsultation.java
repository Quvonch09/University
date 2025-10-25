package univer.university.dto.request;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import univer.university.entity.User;
import univer.university.entity.enums.FinishedEnum;

@Getter
@Setter
public class ReqConsultation {
    private String name;

    private String description;

    private int year;

    private String fileUrl;

    private Long userId;
    private boolean member;
    private FinishedEnum finishedEnum;
    private boolean isLeader;
}
