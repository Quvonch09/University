package univer.university.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import univer.university.service.CloudService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Tag(name = "Files", description = "Filelar bilan ishlash")
public class FileController {
    private final CloudService cloudService;

    @PostMapping(value = "/pdf", consumes = {"multipart/form-data"})
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrlFuture = cloudService.uploadFile(file, file.getOriginalFilename());
            return ResponseEntity.ok(fileUrlFuture);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Xatolik: " + e.getMessage());
        }
    }



    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        String url = cloudService.uploadImage(file);
        return ResponseEntity.ok(url);
    }


}
