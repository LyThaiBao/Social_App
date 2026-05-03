package social_app.example.social_app.mapper;

import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.notification.FriendRequest;
import social_app.example.social_app.dto.notification.NotificationResponse;
import social_app.example.social_app.entity.Notification;

@Component
public class NotificationMapper {

    public NotificationResponse<?> convertToNotificationResponse(Notification notification){
        return NotificationResponse.builder()
                .time(notification.getCreatedAt())
                .type(notification.getType())
                .payload(FriendRequest.builder()
                        .senderId(notification.getSenderId())
                        .senderName(notification.getSenderName())
                        .build()
                )
                .build();
    }
}
