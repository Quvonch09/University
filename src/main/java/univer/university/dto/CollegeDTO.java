package univer.university.dto;

import lombok.*;
import univer.university.dto.response.ResCollage;
import univer.university.dto.response.ResDepartment;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CollegeDTO {
    private Long id;
    private String name;
    private long countUsers;
    private long countProfessor;
    private long countDotsent;
    private long countPHD;
    private long countDSC;
    private long countNull;
    private List<ResDepartment> departmentList;
}
