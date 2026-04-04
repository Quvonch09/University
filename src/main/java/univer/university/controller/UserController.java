package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.UserDTO;
import univer.university.dto.UserStatisticsDto;
import univer.university.dto.request.ReqUserDTO;
import univer.university.dto.response.*;
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


    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<ResDashboard>> getDashboard(){
        return ResponseEntity.ok(userService.getDashboard());
    }


    @GetMapping("/college/{collegeId}")
    public ResponseEntity<ApiResponse<List<ResUser>>> getUsersByCollege(@PathVariable Long collegeId){
        return ResponseEntity.ok(userService.getUsersCollegeId(collegeId));
    }


    @GetMapping("/department/{departmentId}")
    public ResponseEntity<ApiResponse<List<ResUser>>> getUserByDepartment(@PathVariable Long departmentId){
        return ResponseEntity.ok(userService.getUsersDepartmentId(departmentId));
    }

    @GetMapping("/gender-dashboard")
    public ResponseEntity<ApiResponse<GenderStatsProjection>> getGenderDashboard(){
        return ResponseEntity.ok(userService.getGenderDashboard());
    }


    @GetMapping("/age-dashboard")
    public ResponseEntity<ApiResponse<List<AgeGenderStatsProjection>>> getAgeDashboard(){
        return ResponseEntity.ok(userService.getAgeGenderDashboard());
    }




    @GetMapping("/statistics/{userId}")
    public ResponseEntity<ApiResponse<UserStatisticsDto>> getStatistic(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserDashboard(userId));
    }

    @GetMapping("/profile-completion/{userId}")
    public ResponseEntity<ApiResponse<Double>> getProfileCompletion(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getProfileCompletionPercentage(userId));
    }

}
