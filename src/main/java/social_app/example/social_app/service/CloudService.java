package social_app.example.social_app.service;

import org.springframework.web.multipart.MultipartFile;
import social_app.example.social_app.dto.UploadResponse;

public interface CloudService {
    UploadResponse upload(MultipartFile file);
}
