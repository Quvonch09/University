package univer.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqTadqiqot;
import univer.university.service.TadqiqotService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/research")
public class TadqiqotController {

    public final TadqiqotService tadqiqotService;

    @PostMapping
    @Operation(description = "MemberEnum ->  MILLIY, XALQARO")
    public ResponseEntity<ApiResponse<String>> saveTadqiqot(@RequestBody ReqTadqiqot reqTadqiqot) {
        return ResponseEntity.ok(tadqiqotService.saveTadqiqot(reqTadqiqot));
    }


    @PutMapping("/{id}")
    @Operation(description = "MemberEnum ->  MILLIY, XALQARO")
    public ResponseEntity<ApiResponse<String>> updateTadqiqot(@PathVariable Long id, @RequestBody ReqTadqiqot reqTadqiqot) {
        return ResponseEntity.ok(tadqiqotService.updateTadqiqot(id, reqTadqiqot));
    }
}
