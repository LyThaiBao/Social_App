package social_app.example.social_app.service;

import social_app.example.social_app.dto.ChatMessage;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Messages;

public interface ChatService {
    Messages handlePrivateMessage(ChatMessage chatMessage);
    String getUsernameDest(ChatMessage chatMessage);
}
