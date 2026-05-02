package social_app.example.social_app.dto.notification;

import lombok.Builder;
import lombok.Data;
import social_app.example.social_app.entity.Notification;
import social_app.example.social_app.type.NotificationType;

import java.time.Instant;

@Builder
@Data
public class NotificationResponse{
    private NotificationType type;
    private Notification notification;
    private Instant time;
}
