package social_app.example.social_app.dto.notification;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import social_app.example.social_app.entity.Notification;
import social_app.example.social_app.type.NotificationType;

import java.time.Instant;

@Builder
@Data
public class NotificationResponse<T>{
    private Integer id;
    private NotificationType type;
    private T payload;
    private Instant time;
}
