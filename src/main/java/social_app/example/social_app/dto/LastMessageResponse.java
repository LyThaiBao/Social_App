package social_app.example.social_app.dto;

import lombok.Builder;
import lombok.Data;
import social_app.example.social_app.type.MediaType;
import social_app.example.social_app.type.MessageType;

@Data
@Builder
public class LastMessageResponse {
    private String senderName;
    private Integer senderId;
    private String content;
    private MessageType messageType;
    private MediaType mediaType;
    private String lastTime;
}
