package social_app.example.social_app.service;

import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.MessageResponse;
import social_app.example.social_app.dto.notification.NewMessage;
import social_app.example.social_app.dto.notification.Notification;
import social_app.example.social_app.type.NotificationType;


@Service
public class NotificationServiceImp implements NotificationService{
    @Override
    public Notification<NewMessage> newMessageResponse(MessageResponse messageResponse) {
        return Notification.<NewMessage>builder()
                .type(NotificationType.NEW_MESSAGE)
                .payload(NewMessage.builder()
                        .conversationId(messageResponse.getConversationId())
                        .content(messageResponse.getContent())
                        .senderId(messageResponse.getSenderId())
                        .build())
                .build();
    }
}
