package univer.university.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.UserDTO;
import univer.university.dto.request.ReqTeacher;
import univer.university.dto.request.ReqUserDTO;
import univer.university.dto.response.ResPageable;
import univer.university.entity.User;
import univer.university.service.AuthService;
import univer.university.service.UserService;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {
    private final AuthService authService;
    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveUser")
    @Operation(summary = "Teacher saqlash uchun",
            description = "gender = true-erkak, gender = false-ayol")
    public ResponseEntity<ApiResponse<String>> userLogin(
            @RequestBody ReqTeacher register
    ){
        return ResponseEntity.ok(authService.saveUser(register));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.deleteTeacher(userId));
    }


    @PutMapping("/update-profile")
    public ResponseEntity<ApiResponse<String>> update(@AuthenticationPrincipal User user, @RequestBody ReqUserDTO reqUserDto){
        return ResponseEntity.ok(userService.update(user, reqUserDto));
    }



    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDTO>> getById(@PathVariable Long userId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(userService.getById(userId, page, size));
    }


    @GetMapping("/search")
    public ResponseEntity<ApiResponse<ResPageable>> searchUser(@RequestParam(required = false) String name,
                                                               @RequestParam(required = false) String college,
                                                               @RequestParam(required = false) String lavozim,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(userService.searchUsers(name, college, lavozim, page, size));
    }


    @PutMapping("/edit")
    public ResponseEntity<ApiResponse<String>> updateMe(
            @AuthenticationPrincipal User user,
            @RequestBody ReqTeacher dto) {

        return ResponseEntity.ok(userService.updateUser(user, dto));
    }
}