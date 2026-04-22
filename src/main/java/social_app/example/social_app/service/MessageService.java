package social_app.example.social_app.service;

import social_app.example.social_app.dto.MessageResponse;
import social_app.example.social_app.entity.Messages;

import java.util.List;

public interface MessageService {
    Messages save(Messages messages);

    List<MessageResponse> getMessageByConversationId(Integer conversationId);
}
