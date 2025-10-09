package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.UserInfoDTO;
import univer.university.dto.request.ReqUserInfo;
import univer.university.service.UserInfoService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user-info")
public class UserInfoController {
    private final UserInfoService userInfoService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addUserInfo(@RequestBody ReqUserInfo req){
        return ResponseEntity.ok(userInfoService.addUserInfo(req));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserInfoDTO>>> getAllList(){
        return ResponseEntity.ok(userInfoService.getAllList());
    }

    @GetMapping("/{userInfoId}")
    public ResponseEntity<ApiResponse<UserInfoDTO>> getOne(@PathVariable Long userInfoId){
        return ResponseEntity.ok(userInfoService.getUserInfoById(userInfoId));
    }


}
