package social_app.example.social_app.service.msg;

import social_app.example.social_app.dto.msg.LastMessageResponse;
import social_app.example.social_app.dto.msg.MessageResponse;
import social_app.example.social_app.entity.Messages;

import java.util.List;

public interface MessageService {
    Messages save(Messages messages);
    List<MessageResponse> getMessageByConversationId(Integer conversationId);
    LastMessageResponse getLastMessageByConversationId(Integer conversationId);
    MessageResponse getMessageResponse(Messages message);
    Messages getMessageEntityById(Integer id);
    MessageResponse recall(Integer id);

}
