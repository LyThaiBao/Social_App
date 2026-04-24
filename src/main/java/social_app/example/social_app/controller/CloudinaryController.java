package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.service.CloudService;

@RestController
@RequestMapping("/api/cloud")
@RequiredArgsConstructor
public class CloudinaryController {
    private final CloudService cloudService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> upload(@RequestBody MultipartFile file){
        String publicId = this.cloudService.upload(file);
        return ResponseEntity.ok().body(ApiResponse.success("Upload Success",publicId));
    }
}
