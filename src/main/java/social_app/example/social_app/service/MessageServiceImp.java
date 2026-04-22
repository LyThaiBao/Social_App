package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.MessageResponse;
import social_app.example.social_app.entity.Conversations;
import social_app.example.social_app.entity.Messages;
import social_app.example.social_app.mapper.MessageMapper;
import social_app.example.social_app.repo.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImp implements MessageService{
    private final MessageRepository messageRepository;
    private final ConversationService conversationService;
    private final MessageMapper messageMapper;
    @Override
    public Messages save(Messages messages) {
        return this.messageRepository.save(messages);
    }

    @Override
    public List<MessageResponse> getMessageByConversationId(Integer conversationId) {
        // call here for it throw Error and return to client when cvn id null
        Conversations conversation = this.conversationService.getConversationEntityById(conversationId);

        List<Messages> messagesList = this.messageRepository.getMessagesByConversationId(conversationId);

        return messagesList.stream().map(this.messageMapper::convertToMessageResponse).toList();
    }
}
