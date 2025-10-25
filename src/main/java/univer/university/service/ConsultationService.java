package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.ConsultationDTO;
import univer.university.dto.request.ReqConsultation;
import univer.university.dto.request.ReqPage;
import univer.university.dto.response.ResPageable;
import univer.university.entity.Consultation;
import univer.university.entity.User;
import univer.university.exception.DataNotFoundException;
import univer.university.mapper.ConsultationMapper;
import univer.university.repository.ConsultationRepository;
import univer.university.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {
    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;
    private final ConsultationMapper consultationMapper;

    public ApiResponse<String> addConsultation(ReqConsultation req) {

        User user = userRepository.findById(req.getUserId()).orElseThrow(()->new DataNotFoundException("User Not Found"));

        Consultation cons = new Consultation();
        cons.setUser(user);
        cons.setName(req.getName());
        cons.setDescription(req.getDescription());
        cons.setFileUrl(req.getFileUrl());
        cons.setYear(req.getYear());
        cons.setMember(req.isMember());
        cons.setFinishedEnum(req.getFinishedEnum());
        cons.setLeader(req.isLeader());
        consultationRepository.save(cons);

        return ApiResponse.success(null,"success");

    }

    public ApiResponse<ResPageable> getConsByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Consultation> all = consultationRepository.findAll(pageable);
        if(all.isEmpty()){
            return ApiResponse.error("all is empty");
        }
        List<ConsultationDTO> consultationDTOS = all.stream().map(consultationMapper::toConsultationDTO).toList();

        ResPageable resPageable = ResPageable.builder()
                .page(page)
                .size(size)
                .totalPage(all.getTotalPages())
                .totalElements(all.getTotalElements())
                .body(consultationDTOS)
                .build();
        return ApiResponse.success(resPageable,"success");
    }

    public ApiResponse<ConsultationDTO> getConsultationById(Long id){
        Consultation consultation = consultationRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Consultation Not Found"));
        ConsultationDTO consultationDTO = consultationMapper.toConsultationDTO(consultation);
        return ApiResponse.success(consultationDTO,"success");
    }

    public ApiResponse<String> deleteConsultation(Long id){
        Consultation consultation = consultationRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Consultation Not Found"));
        consultationRepository.delete(consultation);
        return ApiResponse.success(null,"success");
    }

}
