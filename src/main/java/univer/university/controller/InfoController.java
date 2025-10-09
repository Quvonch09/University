package univer.university.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import univer.university.dto.ApiResponse;
import univer.university.dto.request.ReqInfo;
import univer.university.dto.request.ReqPage;
import univer.university.dto.response.ResPageable;
import univer.university.service.InfoService;

@Controller
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

}
