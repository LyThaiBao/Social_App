package social_app.example.social_app.dto;

import lombok.Builder;
import lombok.Data;
import social_app.example.social_app.type.MediaType;
import social_app.example.social_app.type.MessageType;

@Builder
@Data
public class UploadResponse {
    private String mediaUrl;
    private MediaType mediaType;
}
