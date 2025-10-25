package univer.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.ConsultationDTO;
import univer.university.dto.request.ReqConsultation;
import univer.university.dto.response.ResPageable;
import univer.university.service.ConsultationService;

@RestController
@RequestMapping("/api/consultation")
@RequiredArgsConstructor
public class ConsultationController {
    private final ConsultationService consultationService;

    @PostMapping
    @Operation(description = "FinishedEnum - > COMPLETED,IN_PROGRESS,FINISHED")
    public ResponseEntity<ApiResponse<String>> saveConsultation(@RequestBody ReqConsultation req) {
        return ResponseEntity.ok(consultationService.addConsultation(req));
    }

    @GetMapping("/get-page")
    public ResponseEntity<ApiResponse<ResPageable>> getConsultationPage(@RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(consultationService.getConsByPage(page,size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConsultationDTO>> getConsultationById(@PathVariable Long id) {
        return ResponseEntity.ok(consultationService.getConsultationById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteConsultationById(@PathVariable Long id) {
        return ResponseEntity.ok(consultationService.deleteConsultation(id));
    }
}
