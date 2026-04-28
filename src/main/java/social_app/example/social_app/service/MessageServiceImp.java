package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.LastMessageResponse;
import social_app.example.social_app.dto.MessageResponse;
import social_app.example.social_app.entity.Conversations;
import social_app.example.social_app.entity.Messages;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.mapper.MessageMapper;
import social_app.example.social_app.repo.MessageRepository;
import social_app.example.social_app.util.ConvertDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImp implements MessageService{
    private final MessageRepository messageRepository;
    private final ConversationService conversationService;
    private final MessageMapper messageMapper;
    private final ConvertDateTime convertDateTime;
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

    @Override
    public LastMessageResponse getLastMessageByConversationId(Integer conversationId) {
        // call here for it throw Error and return to client when cvn id null
        Conversations conversation = this.conversationService.getConversationEntityById(conversationId);
        //---------------------------------------------------------------------------------------------
        List<Messages> messagesList = this.messageRepository.getMessagesByConversationId(conversationId);

        return this.messageMapper.convertToLastMessageResponse(messagesList.getLast());
    }

    @Override
    public MessageResponse getMessageResponse(Messages messageSaved) {
        MessageResponse.MessageResponseBuilder messageResponse = MessageResponse.builder()
                .id(messageSaved.getId())
                .content(messageSaved.getContent())
                .mediaUrl(messageSaved.getMediaUrl())
                .mediaType(messageSaved.getMediaType())
                .sentTime(this.convertDateTime.convertInstant(messageSaved.getCreatedAt()))
                .senderId(messageSaved.getSender().getId())
                .messageType(messageSaved.getType())
                .senderName(messageSaved.getSender().getFullName())
                .conversationId(messageSaved.getConversation().getId());
            // did not build

        if(messageSaved.getParentMessage() != null){
            messageResponse.parentId(messageSaved.getParentMessage().getId());
            messageResponse.parentMessageContent(messageSaved.getParentMessage().getContent());
            messageResponse.parentMessageSenderName(messageSaved.getParentMessage().getSender().getFullName());
            messageResponse.parentMediaType(messageSaved.getParentMessage().getMediaType());
        }
        return messageResponse.build(); // built
    }

    @Override
    public Messages getMessageEntityById(Integer id) {
        return this.messageRepository.findById(id).orElseThrow(()->new NotFoundResource("Not found parent message"));
    }
}
