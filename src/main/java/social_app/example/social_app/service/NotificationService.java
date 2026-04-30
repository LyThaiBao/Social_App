package social_app.example.social_app.service;

import social_app.example.social_app.dto.MessageResponse;
import social_app.example.social_app.dto.notification.NewMessage;
import social_app.example.social_app.dto.notification.Notification;

public interface NotificationService {
    Notification<NewMessage> newMessageResponse(MessageResponse messageResponse);
}
