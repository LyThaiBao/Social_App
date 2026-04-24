package social_app.example.social_app.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudService {
    String upload(MultipartFile file);
}
