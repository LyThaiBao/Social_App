package social_app.example.social_app.dto.cloud;

import lombok.Builder;
import lombok.Data;
import social_app.example.social_app.type.MediaType;

@Builder
@Data
public class UploadResponse {
    private String mediaUrl;
    private MediaType mediaType;
}
