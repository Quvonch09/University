package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.response.ResLavozim;
import univer.university.service.LavozimService;

import java.util.List;

@RestController
@RequestMapping("/lavozim")
@RequiredArgsConstructor
public class LavozimController {

    private final LavozimService lavozimService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResLavozim>>> getAll(){
        return ResponseEntity.ok(lavozimService.getAllLavozim());
    }


    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveLavozim(@RequestBody ResLavozim resLavozim){
        return ResponseEntity.ok(lavozimService.saveLavozim(resLavozim));
    }



    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateLavozim(@PathVariable Long id, @RequestBody ResLavozim resLavozim){
        return ResponseEntity.ok(lavozimService.updateLavozim(id, resLavozim));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteLavozim(@PathVariable Long id){
        return ResponseEntity.ok(lavozimService.deleteLavozim(id));
    }
}
