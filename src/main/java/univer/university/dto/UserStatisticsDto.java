package univer.university.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStatisticsDto {
    private long tadqiqotlar;
    private long nashrlar;
    private long maqolalar;
    private long kitoblar;
    private long ishYuritishlar;
    private long boshqalar;
    private long nazorat;
    private long maslahatlar;
    private long mukofotlar;
    private long treninglar;
    private long tahririyatAzolik;
    private long maxsusKengash;
    private long patentlar;
    private long davlatMukofotlari;
}
