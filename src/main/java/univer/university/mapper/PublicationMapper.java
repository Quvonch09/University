package univer.university.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import univer.university.dto.PublicationDTO;
import univer.university.entity.Publication;
import univer.university.entity.User;
import univer.university.exception.DataNotFoundException;
import univer.university.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class PublicationMapper {

    private final UserRepository userRepository;

    public PublicationDTO toPublicationDTO(Publication publication) {

        User user = userRepository.findById(publication.getUser().getId()).orElseThrow(()->new DataNotFoundException("User Not Found"));

        return PublicationDTO.builder()
                .id(publication.getId())
                .userId(user.getId())
                .name(publication.getName())
                .description(publication.getDescription())
                .year(publication.getYear())
                .fileUrl(publication.getFileUrl())
                .type(publication.getType())
                .author(publication.getAuthor())
                .degree(publication.getDegree())
                .volume(publication.getVolume())
                .institution(publication.getInstitution())
                .popular(publication.isPopular())
                .build();
    }
}
