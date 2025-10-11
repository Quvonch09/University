package univer.university.mapper;

import org.springframework.stereotype.Component;
import univer.university.dto.InfoDTO;
import univer.university.entity.Info;

@Component
public class InfoMapper {
    public InfoDTO infoDTO(Info info) {
        return InfoDTO.builder()
                .id(info.getId())
                .object(info.getObject())
                .categoryId(info.getCategory().getId())
                .build();
    }
}
