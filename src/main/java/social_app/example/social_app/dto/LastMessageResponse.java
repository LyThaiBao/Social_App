package social_app.example.social_app.dto;

import lombok.Builder;
import lombok.Data;
import social_app.example.social_app.entity.MessageType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class LastMessageResponse {
    private String content;
    private MessageType messageType;
    private String lastTime;
}
