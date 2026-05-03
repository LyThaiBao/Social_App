package social_app.example.social_app.dto.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationRequest {
    private Integer memberId;
}
