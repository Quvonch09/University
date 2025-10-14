package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import univer.university.dto.ApiResponse;
import univer.university.dto.InfoDTO;
import univer.university.dto.request.ReqInfo;
import univer.university.dto.request.ReqPage;
import univer.university.dto.request.ReqUpdateInfo;
import univer.university.dto.response.ResPageable;
import univer.university.service.InfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
public class InfoController {
    private final InfoService infoService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addInfo(@RequestBody ReqInfo reqInfo) {
        return ResponseEntity.ok(infoService.addInfo(reqInfo));

    }

    @PostMapping("/get-page")
    public ResponseEntity<ApiResponse<ResPageable>> getAllPage(@RequestBody ReqPage reqPage) {
        return ResponseEntity.ok(infoService.getPageInfo(reqPage));

    }

    @PutMapping
    public ResponseEntity<ApiResponse<String>> updateInfo(@RequestBody ReqUpdateInfo reqInfo) {
        return ResponseEntity.ok(infoService.updateInfo(reqInfo));
    }

    @GetMapping("/{infoId}")
    public ResponseEntity<ApiResponse<InfoDTO>> getOneInfo(@PathVariable Long infoId) {
        return ResponseEntity.ok(infoService.getInfoById(infoId));

    }

}
