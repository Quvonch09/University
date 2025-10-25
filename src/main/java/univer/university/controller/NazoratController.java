package univer.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqNazorat;
import univer.university.dto.request.ReqTadqiqot;
import univer.university.dto.response.ResPageable;
import univer.university.service.NazoratService;
import univer.university.service.TadqiqotService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nazorat")
public class NazoratController {

    public final NazoratService nazoratService;

    @PostMapping
    @Operation(description = "MemberEnum ->  MILLIY, XALQARO")
    public ResponseEntity<ApiResponse<String>> saveTadqiqot(@RequestBody ReqNazorat reqNazorat) {
        return ResponseEntity.ok(nazoratService.saveNazorat(reqNazorat));
    }


    @PutMapping("/{id}")
    @Operation(description = "MemberEnum ->  MILLIY, XALQARO")
    public ResponseEntity<ApiResponse<String>> updateTadqiqot(@PathVariable Long id, @RequestBody ReqNazorat reqNazorat) {
        return ResponseEntity.ok(nazoratService.updateNazorat(id, reqNazorat));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTadqiqot(@PathVariable Long id) {
        return ResponseEntity.ok(nazoratService.deleteNazorat(id));
    }



    @GetMapping
    public ResponseEntity<ApiResponse<ResPageable>> getAll(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(nazoratService.getAll(page,size));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReqNazorat>> getTadqiqot(@PathVariable Long id) {
        return ResponseEntity.ok(nazoratService.getOne(id));
    }
}
