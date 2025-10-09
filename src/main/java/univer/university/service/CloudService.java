package univer.university.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class CloudService {

    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.api_key}")
    private String supabaseApiKey;

    @Value("${supabase.bucket_name}")
    private String bucketName;

    private final RestTemplate restTemplate;

    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        String uniqueName = LocalDateTime.now() + "_" + fileName;
        String filePath = "uploads/" + uniqueName;

        String uploadUrl = String.format("%s/storage/v1/object/%s/%s", supabaseUrl, bucketName, filePath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(Objects.requireNonNull(file.getContentType())));
        headers.set("Authorization", "Bearer " + supabaseApiKey);

        HttpEntity<byte[]> entity = new HttpEntity<>(file.getBytes(), headers);

        ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return supabaseUrl + "/storage/v1/object/public/" + bucketName + "/" + filePath;
        } else {
            throw new BadRequestException("Fayl yuklashda xatolik: " + response.getStatusCode());
        }
    }
}
