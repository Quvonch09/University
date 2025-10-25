package univer.university.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import univer.university.dto.request.ReqNazorat;
import univer.university.entity.Nazorat;

@Component
@RequiredArgsConstructor
public class NazoratMapper {
    public ReqNazorat reqNazorat(Nazorat nazorat) {
        return ReqNazorat.builder()
                .id(nazorat.getId())
                .userId(nazorat.getUser().getId())
                .univerName(nazorat.getUniverName())
                .level(nazorat.getLevel())
                .year(nazorat.getYear())
                .description(nazorat.getDescription())
                .fileUrl(nazorat.getFileUrl())
                .finished(nazorat.isFinished())
                .memberEnum(nazorat.getMemberEnum())
                .researcherName(nazorat.getResearcherName())
                .name(nazorat.getName())
                .build();
    }
}
