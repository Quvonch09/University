package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.UserDTO;
import univer.university.dto.response.AgeGenderStatsProjection;
import univer.university.dto.response.GenderStatsProjection;
import univer.university.dto.response.ResDashboard;
import univer.university.entity.User;
import univer.university.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserDTO>> getMe(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.getMe(user));
    }


    @PutMapping
    public ResponseEntity<ApiResponse<String>> update(@AuthenticationPrincipal User user, @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.update(user, userDTO));
    }


    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<ResDashboard>> getDashboard(){
        return ResponseEntity.ok(userService.getDashboard());
    }


    @GetMapping("/gender-dashboard")
    public ResponseEntity<ApiResponse<GenderStatsProjection>> getGenderDashboard(){
        return ResponseEntity.ok(userService.getGenderDashboard());
    }


    @GetMapping("/age-dashboard")
    public ResponseEntity<ApiResponse<List<AgeGenderStatsProjection>>> getAgeDashboard(){
        return ResponseEntity.ok(userService.getAgeGenderDashboard());
    }
}
