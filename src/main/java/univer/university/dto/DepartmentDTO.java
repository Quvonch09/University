package univer.university.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDTO {
    private Long id;
    private String name;
    private String imgUrl;
    private long countUsers;
    private long countProfessor;
    private long countDotsent;
    private long countPHD;
    private long countDSC;
    private long countNull;
}
