package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.IlmiyDarajaStatsDTO;
import univer.university.dto.request.ReqIlmiyDaraja;
import univer.university.dto.response.ResIlmiyDaraja;
import univer.university.service.IlmiyDarajaService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ilmiy-daraja")
@RequiredArgsConstructor
public class IlmiyDarajaController {

    private final IlmiyDarajaService ilmiyDarajaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResIlmiyDaraja>>> getAll() {
        return ResponseEntity.ok(ilmiyDarajaService.getAllIlmiyDaraja());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveIlmiyDaraja(@RequestBody ReqIlmiyDaraja req) {
        return ResponseEntity.ok(ilmiyDarajaService.saveIlmiyDaraja(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateIlmiyDaraja(@PathVariable Long id,
                                                                  @RequestBody ReqIlmiyDaraja req) {
        return ResponseEntity.ok(ilmiyDarajaService.updateIlmiyDaraja(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteIlmiyDaraja(@PathVariable Long id) {
        return ResponseEntity.ok(ilmiyDarajaService.deleteIlmiyDaraja(id));
    }

    @GetMapping("/get-ilmiy-daraja-statistiks")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getIlmiyDarajaStatistics() {
        return ResponseEntity.ok(ilmiyDarajaService.getIlmiyDarajaStatistics());
    }


    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<List<IlmiyDarajaStatsDTO>>> getIlmiyDarajaStats() {
        return ResponseEntity.ok(ilmiyDarajaService.getStats());
    }
}