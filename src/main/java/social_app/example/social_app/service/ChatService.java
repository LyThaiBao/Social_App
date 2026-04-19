package social_app.example.social_app.service;

import social_app.example.social_app.dto.ChatMessage;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Messages;

import java.security.Principal;

public interface ChatService {
    Messages saveMessage(ChatMessage chatMessage);
    String getUsernameDest(ChatMessage chatMessage);

}
