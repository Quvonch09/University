package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.request.ReqAward;
import univer.university.entity.Award;

@Component
public class AwardMapper {
    public ReqAward reqAward(Award award){
        return ReqAward.builder()
                .id(award.getId())
                .name(award.getName())
                .description(award.getDescription())
                .year(award.getYear())
                .memberEnum(award.getMemberEnum())
                .input(award.getInput())
                .fileUrl(award.getFileUrl())
                .userId(award.getUser().getId())
                .build();
    }
}
