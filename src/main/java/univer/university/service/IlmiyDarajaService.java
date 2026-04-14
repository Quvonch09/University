package univer.university.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import univer.university.dto.ApiResponse;
import univer.university.dto.IlmiyDarajaStatsDTO;
import univer.university.dto.UserDTO;
import univer.university.dto.request.ReqIlmiyDaraja;
import univer.university.dto.response.ResIlmiyDaraja;
import univer.university.dto.response.ResIlmiyDarajaStatistics;
import univer.university.entity.IlmiyDaraja;
import univer.university.entity.User;
import univer.university.exception.DataNotFoundException;
import univer.university.repository.IlmiyDarajaRepository;
import univer.university.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IlmiyDarajaService {

    private final IlmiyDarajaRepository ilmiyDarajaRepository;
    private final UserRepository userRepository;

    public ApiResponse<String> saveIlmiyDaraja(ReqIlmiyDaraja req) {
        if (ilmiyDarajaRepository.existsByName(req.getName())) {
            return ApiResponse.error("Bu nom allaqachon mavjud");
        }

        IlmiyDaraja ilmiyDaraja = new IlmiyDaraja(req.getName());
        ilmiyDarajaRepository.save(ilmiyDaraja);
        return ApiResponse.success(null, "Muvaffaqiyatli saqlandi");
    }

    public ApiResponse<String> updateIlmiyDaraja(Long id, ReqIlmiyDaraja req) {
        IlmiyDaraja ilmiyDaraja = ilmiyDarajaRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Ilmiy daraja topilmadi")
        );

        if (ilmiyDarajaRepository.existsByName(req.getName())) {
            return ApiResponse.error("Bu nom allaqachon mavjud");
        }

        ilmiyDaraja.setName(req.getName());
        ilmiyDarajaRepository.save(ilmiyDaraja);
        return ApiResponse.success(null, "Muvaffaqiyatli yangilandi");
    }

    public ApiResponse<String> deleteIlmiyDaraja(Long id) {
        IlmiyDaraja ilmiyDaraja = ilmiyDarajaRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Ilmiy daraja topilmadi")
        );

        boolean exists = userRepository.existsByIlmiyDaraja_Id(ilmiyDaraja.getId());
        if (exists) {
            return ApiResponse.error("O'chirish mumkin emas, foydalanuvchilar bog'liq");
        }

        ilmiyDarajaRepository.delete(ilmiyDaraja);
        return ApiResponse.success(null, "Muvaffaqiyatli o'chirildi");
    }

    public ApiResponse<List<ResIlmiyDaraja>> getAllIlmiyDaraja() {
        List<ResIlmiyDaraja> list = ilmiyDarajaRepository.findAll()
                .stream()
                .map(this::toResIlmiyDaraja)
                .toList();
        return ApiResponse.success(list, "Muvaffaqiyatli");
    }

    public ApiResponse<ResIlmiyDaraja> getByIdIlmiyDaraja(Long id) {
        IlmiyDaraja ilmiyDaraja = ilmiyDarajaRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Ilmiy daraja topilmadi")
        );
        return ApiResponse.success(toResIlmiyDaraja(ilmiyDaraja), "Muvaffaqiyatli");
    }

    public ApiResponse<Map<String, Object>> getIlmiyDarajaStatistics() {
        List<Object[]> rows = ilmiyDarajaRepository.getIlmiyDarajaStats();

        List<ResIlmiyDarajaStatistics> list = rows.stream()
                .map(r -> new ResIlmiyDarajaStatistics(
                        (String) r[0],
                        ((Number) r[1]).intValue()
                ))
                .toList();

        Long total = list.stream().mapToLong(ResIlmiyDarajaStatistics::getTotalEmployees).sum();

        Map<String, Object> response = new HashMap<>();
        response.put("total", total);
        response.put("data", list);

        return ApiResponse.success(response, "Muvaffaqiyatli");
    }

    private ResIlmiyDaraja toResIlmiyDaraja(IlmiyDaraja ilmiyDaraja) {
        return ResIlmiyDaraja.builder()
                .id(ilmiyDaraja.getId())
                .name(ilmiyDaraja.getName())
                .build();
    }


    public ApiResponse<List<IlmiyDarajaStatsDTO>> getStats() {

        List<IlmiyDaraja> darajalar = ilmiyDarajaRepository.findAll();

        List<IlmiyDarajaStatsDTO> result = new ArrayList<>();

        for (IlmiyDaraja daraja : darajalar) {

            List<User> users = userRepository.findAllByIlmiyDaraja_Id(daraja.getId());

            List<UserDTO> userDTOS = users.stream().map(u ->
                    UserDTO.builder()
                            .id(u.getId())
                            .fullName(u.getFullName())
                            .email(u.getEmail())
                            .phone(u.getPhone())
                            .build()
            ).toList();

            result.add(
                    IlmiyDarajaStatsDTO.builder()
                            .ilmiyDarajaName(daraja.getName())
                            .userCount((long) users.size())
                            .users(userDTOS)
                            .build()
            );
        }

        return ApiResponse.success(result, "Success");
    }
}