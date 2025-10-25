package univer.university.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import univer.university.dto.request.ReqTadqiqot;
import univer.university.entity.Tadqiqot;

@Component
@RequiredArgsConstructor
public class TadqiqotMapper {
    public ReqTadqiqot reqTadqiqot(Tadqiqot tadqiqot){
        return ReqTadqiqot.builder()
                .id(tadqiqot.getId())
                .name(tadqiqot.getName())
                .year(tadqiqot.getYear())
                .fileUrl(tadqiqot.getFileUrl())
                .description(tadqiqot.getDescription())
                .finished(tadqiqot.isFinished())
                .member(tadqiqot.isMember())
                .userId(tadqiqot.getUser().getId())
                .memberEnum(tadqiqot.getMemberEnum())
                .univerName(tadqiqot.getUniverName())
                .build();
    }
}
