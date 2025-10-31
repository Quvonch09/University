package univer.university.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import univer.university.entity.base.BaseEntity;

import java.time.Year;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AcademicQualification extends BaseEntity {

    // Masalan: Oliy, PhD, DSc, Dotsent, Professor
    @Column(nullable = false)
    private String degreeLevel;

    private String specialization;

    private int startYear;

    private int endYear;

    private String institutionName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    private User user;
}
