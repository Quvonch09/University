package univer.university.dto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IlmiyDarajaStatsDTO {

    private String ilmiyDarajaName;
    private Long userCount;
    private List<UserDTO> users;

}
