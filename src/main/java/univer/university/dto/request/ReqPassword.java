package univer.university.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqPassword {
    private Long userId;
    private String password;
}
