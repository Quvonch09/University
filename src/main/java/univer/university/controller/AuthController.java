package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqPassword;
import univer.university.entity.User;
import univer.university.service.AuthService;
import univer.university.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> adminLogin(
            @RequestParam String phone,
            @RequestParam String password
    ){
        return ResponseEntity.ok(authService.login(phone, password));
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping("/saveStudent")
//    public ResponseEntity<ApiResponse<String>> studentLogin(@RequestBody ReqStudent reqStudent){
//        return ResponseEntity.ok(authService.saveStudent(reqStudent));
//    }


    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@AuthenticationPrincipal User user,
                                                              @RequestBody ReqPassword reqPassword){
        return ResponseEntity.ok(userService.updatePassword(user, reqPassword));
    }
}