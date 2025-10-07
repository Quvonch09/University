package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import univer.university.dto.ApiResponse;
import univer.university.dto.UserDTO;
import univer.university.entity.User;
import univer.university.service.UserService;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserDTO>> getMe(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.getMe(user));
    }

}
