package univer.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.CollegeDTO;
import univer.university.dto.request.ReqCollage;
import univer.university.dto.response.ResCollage;
import univer.university.dto.response.ResCollegeDashboard;
import univer.university.dto.response.ResPageable;
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
    public ResponseEntity<ApiResponse<List<ResCollage>>> getAllColleges(@RequestParam(required = false) String name){
        return ResponseEntity.ok(collageService.getCollage(name));
    }


    @GetMapping("/{collegeId}")
    public ResponseEntity<ApiResponse<CollegeDTO>> getOneCollege(@PathVariable Long collegeId){
        return ResponseEntity.ok(collageService.getOneCollege(collegeId));
    }

    @GetMapping("/college-dashboard")
    public ResponseEntity<ApiResponse<ResCollegeDashboard>> getDashboard(){
        return ResponseEntity.ok(collageService.getCollegeDashboard());
    }


    @GetMapping("/page")
    @Operation(summary = "College pagenation bilan ishlaydigan api")
    public ResponseEntity<ApiResponse<ResPageable>> getPage(@RequestParam(required = false) String name,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(collageService.getCollegePageable(name, page, size));
    }
}
