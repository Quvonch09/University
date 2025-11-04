package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.AcademicQualificationDTO;
import univer.university.dto.ApiResponse;
import univer.university.service.AcademicQualificationService;

import java.util.List;

@RestController
@RequestMapping("/academicQualification")
@RequiredArgsConstructor
public class AcademicQualificationController {
    private final AcademicQualificationService academicQualificationService;


    @PostMapping
    public ResponseEntity<ApiResponse<String>> save(@RequestBody AcademicQualificationDTO academicQualificationDTO){
        return ResponseEntity.ok(academicQualificationService.save(academicQualificationDTO));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> update(@PathVariable Long id,
                                                      @RequestBody AcademicQualificationDTO academicQualificationDTO){
        return ResponseEntity.ok(academicQualificationService.update(id, academicQualificationDTO));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id){
        return ResponseEntity.ok(academicQualificationService.delete(id));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<AcademicQualificationDTO>>> getAll(){
        return ResponseEntity.ok(academicQualificationService.getAll());
    }



    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AcademicQualificationDTO>> getById(@PathVariable Long id){
        return ResponseEntity.ok(academicQualificationService.getById(id));
    }
}
