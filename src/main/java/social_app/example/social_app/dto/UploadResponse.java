package social_app.example.social_app.dto;

import lombok.Builder;
import lombok.Data;
import social_app.example.social_app.entity.MessageType;

@Builder
@Data
public class UploadResponse {
    private String mediaUrl;
    private MessageType mediaType;
}
