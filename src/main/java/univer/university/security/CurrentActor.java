package univer.university.security;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentActor {

    private Long id;
    private String role;
    private String fullName;
    private String type;
    private boolean anonymous;

    public static CurrentActor anonymous() {
        return CurrentActor.builder()
                .id(null)
                .role("ANONYMOUS")
                .fullName("Anonymous")
                .type("ANONYMOUS")
                .anonymous(true)
                .build();
    }
}
