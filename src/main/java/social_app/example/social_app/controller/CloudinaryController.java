package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.service.CloudService;

@RestController
@RequestMapping("/api/cloud")
@RequiredArgsConstructor
@Slf4j
public class CloudinaryController {
    private final CloudService cloudService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> upload(@RequestParam("file") MultipartFile file){
        log.info("LOG >>>");
        
        String publicId = this.cloudService.upload(file);
        return ResponseEntity.ok().body(ApiResponse.success("Upload Success",publicId));
    }
}
