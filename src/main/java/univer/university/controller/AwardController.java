package univer.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqAward;
import univer.university.dto.request.ReqNazorat;
import univer.university.dto.response.ResPageable;
import univer.university.service.AwardService;
import univer.university.service.NazoratService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/award")
public class AwardController {

    public final AwardService awardService;

    @PostMapping
    @Operation(description = "MemberEnum ->  MILLIY, XALQARO")
    public ResponseEntity<ApiResponse<String>> saveTadqiqot(@RequestBody ReqAward reqAward) {
        return ResponseEntity.ok(awardService.saveAward(reqAward));
    }


    @PutMapping("/{id}")
    @Operation(description = "MemberEnum ->  MILLIY, XALQARO")
    public ResponseEntity<ApiResponse<String>> updateTadqiqot(@PathVariable Long id, @RequestBody ReqAward reqAward) {
        return ResponseEntity.ok(awardService.updateAward(id, reqAward));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTadqiqot(@PathVariable Long id) {
        return ResponseEntity.ok(awardService.deleteAward(id));
    }



    @GetMapping
    public ResponseEntity<ApiResponse<ResPageable>> getAll(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(awardService.getAll(page,size));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReqAward>> getTadqiqot(@PathVariable Long id) {
        return ResponseEntity.ok(awardService.getAwardById(id));
    }


    @GetMapping("/byUser/{id}")
    public ResponseEntity<ApiResponse<ResPageable>> getAwardByUser(@PathVariable Long id,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(awardService.getByUserId(id,page,size));
    }
}
