package univer.university.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResCollegeDashboard {
    private long countDepartments;
    private long countInfo;
    private long countTeachers;
    private long countInfoByMonth;
}
