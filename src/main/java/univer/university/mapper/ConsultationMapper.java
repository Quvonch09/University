package univer.university.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import univer.university.dto.ConsultationDTO;
import univer.university.entity.Consultation;
import univer.university.entity.User;
import univer.university.exception.DataNotFoundException;
import univer.university.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class ConsultationMapper {

    private final UserRepository userRepository;

    public ConsultationDTO toConsultationDTO(Consultation consultation) {

        User user = userRepository.findById(consultation.getUser().getId()).orElseThrow(() -> new DataNotFoundException("User Not Found"));

        return ConsultationDTO.builder()
                .id(consultation.getId())
                .userId(user.getId())
                .name(consultation.getName())
                .description(consultation.getDescription())
                .year(consultation.getYear())
                .fileUrl(consultation.getFileUrl())
                .finishedEnum(consultation.getFinishedEnum())
                .leader(consultation.getLeader())
                .build();
    }
}
