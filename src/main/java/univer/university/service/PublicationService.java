package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.PublicationDTO;
import univer.university.dto.request.ReqPage;
import univer.university.dto.request.ReqPublication;
import univer.university.dto.response.ResPageable;
import univer.university.entity.Publication;
import univer.university.entity.User;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.PublicationMapper;
import univer.university.repository.PublicationRepository;
import univer.university.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final UserRepository userRepository;
    private final PublicationMapper publicationMapper;

    public ApiResponse<String> addPublication(ReqPublication req) {

        User user = userRepository.findById(req.getUserId()).orElseThrow(()->new DataNotFoundException("User Not Found"));
        Publication publication = Publication.builder()
                .user(user)
                .name(req.getName())
                .description(req.getDescription())
                .year(req.getYear())
                .fileUrl(req.getFileUrl())
                .type(req.getType())
                .author(req.getAuthor())
                .degree(req.getDegree())
                .volume(req.getVolume())
                .institution(req.getInstitution())
                .popular(req.isPopular())
                .build();
        publicationRepository.save(publication);
        return ApiResponse.success(null,"success");
    }

    public ApiResponse<ResPageable> getAllPage(int page, int size) {
        Page<Publication> all = publicationRepository.findAll(PageRequest.of(page, size));
        return check(all, page,size);
    }

    public ApiResponse<String> updatePublication(Long id,ReqPublication req) {

        Publication publication = publicationRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Publication Not Found"));

        User user = userRepository.findById(req.getUserId()).orElseThrow(() -> new DataNotFoundException("User Not Found"));

        publication.setUser(user);
        publication.setName(req.getName());
        publication.setDescription(req.getDescription());
        publication.setYear(req.getYear());
        publication.setFileUrl(req.getFileUrl());
        publication.setType(req.getType());
        publication.setAuthor(req.getAuthor());
        publication.setDegree(req.getDegree());
        publication.setVolume(req.getVolume());
        publication.setInstitution(req.getInstitution());
        publication.setPopular(req.isPopular());
        publicationRepository.save(publication);
        return ApiResponse.success(null,"success");
    }

    public ApiResponse<PublicationDTO> getPublicationById(Long id) {

        Publication publication = publicationRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Publication Not Found"));
        PublicationDTO publicationDTO = publicationMapper.toPublicationDTO(publication);
        return ApiResponse.success(publicationDTO,"success");
    }

    public ApiResponse<String> delete(Long id) {
        Publication publication = publicationRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Publication Not Found"));
        publicationRepository.delete(publication);
        return ApiResponse.success(null,"success");
    }


    public ApiResponse<ResPageable> getByUserId(Long userId, int page, int size) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new DataNotFoundException("User Not Found")
        );

        Page<Publication> publications = publicationRepository.findAllByUser(user.getId(), PageRequest.of(page, size));
        return check(publications, page,size);
    }




    private ApiResponse<ResPageable> check(Page<Publication> publications, int page, int size){
//        if (publications.isEmpty()){
//            return ApiResponse.error("Publication is Empty");
//        }

        List<PublicationDTO> publicationDTOS = publications.stream().map(publicationMapper::toPublicationDTO).toList();

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(publications.getTotalPages())
                .totalElements(publications.getTotalElements())
                .body(publicationDTOS)
                .build();
        return ApiResponse.success(resPageable,"success");


    }
}
