package social_app.example.social_app.dto.notification;

import lombok.Builder;
import lombok.Data;
import social_app.example.social_app.type.NotificationType;

import java.sql.Timestamp;

@Builder
@Data
public class Notification <T>{
    private NotificationType type;
    private T payload;
    private Timestamp timestamp;
}
