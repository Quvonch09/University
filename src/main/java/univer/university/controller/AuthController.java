package univer.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.AuthRegister;
import univer.university.entity.enums.AcademicTitle;
import univer.university.entity.enums.Level;
import univer.university.entity.enums.Role;
import univer.university.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> adminLogin(
            @RequestParam String phone,
            @RequestParam String password
    ){
        return ResponseEntity.ok(authService.login(phone, password));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveUser")
    @Operation(summary = "Teacher saqlash uchun",
            description = "gender = true-erkak, gender = false-ayol")
    public ResponseEntity<ApiResponse<String>> userLogin(
            @RequestParam Role role,
            @RequestParam(required = false) AcademicTitle academicTitle,
            @RequestParam(required = false) Level level,
            @RequestBody AuthRegister register
    ){
        return ResponseEntity.ok(authService.saveUser(register, role, academicTitle, level));
    }


//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/saveStudent")
//    public ResponseEntity<ApiResponse<String>> studentLogin(@RequestBody ReqStudent reqStudent){
//        return ResponseEntity.ok(authService.saveStudent(reqStudent));
//    }
}
