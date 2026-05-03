package social_app.example.social_app.service.notifi;

import social_app.example.social_app.dto.friendship.FriendShipResponse;
import social_app.example.social_app.dto.msg.MessageResponse;
import social_app.example.social_app.dto.notification.FriendRequest;
import social_app.example.social_app.dto.notification.NewMessage;
import social_app.example.social_app.dto.notification.NotificationResponse;

import java.util.List;

public interface NotificationService {
    NotificationResponse<?> newMessageResponse(MessageResponse messageResponse);
    NotificationResponse<?> friendRequest(FriendShipResponse friendShipResponse);
    List<NotificationResponse<?>> getNotificationByMemberId(Integer memberId);
}
