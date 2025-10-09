package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqCollage;
import univer.university.dto.response.ResCollage;
import univer.university.service.CollageService;

import java.util.List;

@RestController
@RequestMapping("/college")
@RequiredArgsConstructor
public class CollageController {
    private final CollageService collageService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<String>> saveCollege(@RequestBody ReqCollage reqCollage){
        return ResponseEntity.ok(collageService.saveCollage(reqCollage));
    }


    @PutMapping("/{collegeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateCollege(@PathVariable Long collegeId,
                                                             @RequestBody ReqCollage reqCollage){
        return ResponseEntity.ok(collageService.updateCollage(collegeId, reqCollage));
    }


    @DeleteMapping("/{collegeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteCollege(@PathVariable Long collegeId){
        return ResponseEntity.ok(collageService.deleteCollage(collegeId));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<ResCollage>>> getAllColleges(){
        return ResponseEntity.ok(collageService.getCollage());
    }
}
