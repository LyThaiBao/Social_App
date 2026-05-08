package social_app.example.social_app.service.chat;

import social_app.example.social_app.dto.msg.ChatMessage;
import social_app.example.social_app.dto.msg.RecallMessageRequest;
import social_app.example.social_app.entity.Messages;

import java.security.Principal;

public interface ChatService {
    Messages saveMessage(ChatMessage chatMessage);
    String getUsernameDest(ChatMessage chatMessage);
    String getUsernameDest(RecallMessageRequest recallMessageRequest, Principal principal);

}
