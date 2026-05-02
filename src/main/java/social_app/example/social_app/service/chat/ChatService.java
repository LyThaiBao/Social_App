package social_app.example.social_app.service.chat;

import social_app.example.social_app.dto.msg.ChatMessage;
import social_app.example.social_app.entity.Messages;

public interface ChatService {
    Messages saveMessage(ChatMessage chatMessage);
    String getUsernameDest(ChatMessage chatMessage);

}
