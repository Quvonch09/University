package univer.university.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResDashboard {
    private long countAllUsers;
    private long countMale;
    private long countFemale;
    private long countAcademic;
}
