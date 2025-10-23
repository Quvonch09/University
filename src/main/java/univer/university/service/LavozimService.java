package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.response.ResLavozim;
import univer.university.entity.Lavozm;
import univer.university.exception.DataNotFoundException;
import univer.university.repository.LavozimRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LavozimService {
    private final LavozimRepository lavozimRepository;

    public ApiResponse<String> saveLavozim(ResLavozim resLavozim){
        boolean b = lavozimRepository.existsByName(resLavozim.getName());
        if (b){
            return ApiResponse.error("This name already exists");
        }

        Lavozm lavozm = new Lavozm(resLavozim.getName());
        lavozimRepository.save(lavozm);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String> updateLavozim(Long id, ResLavozim resLavozim){
        Lavozm lavozm = lavozimRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Lavozim not found")
        );

        if (lavozimRepository.existsByName(resLavozim.getName())){
            return ApiResponse.error("This name already exists");
        }

        lavozm.setName(resLavozim.getName());
        lavozimRepository.save(lavozm);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<String> deleteLavozim(Long id){
        Lavozm lavozm = lavozimRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Lavozim not found")
        );
        lavozimRepository.delete(lavozm);
        return ApiResponse.success(null, "Success");
    }



    public ApiResponse<List<ResLavozim>> getAllLavozim(){
        List<ResLavozim> resLavozims = lavozimRepository.findAll().stream().map(this::resLavozim).toList();
        return ApiResponse.success(resLavozims, "Success");
    }


    private ResLavozim resLavozim(Lavozm lavozm){
        return ResLavozim.builder()
                .id(lavozm.getId())
                .name(lavozm.getName())
                .build();
    }
}
