package social_app.example.social_app.service.cloud;

import org.springframework.web.multipart.MultipartFile;
import social_app.example.social_app.dto.cloud.UploadResponse;

public interface CloudService {
    UploadResponse upload(MultipartFile file);
}
