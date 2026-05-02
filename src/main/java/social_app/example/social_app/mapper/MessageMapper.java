package social_app.example.social_app.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.msg.LastMessageResponse;
import social_app.example.social_app.dto.msg.MessageResponse;
import social_app.example.social_app.entity.Messages;
import social_app.example.social_app.util.ConvertDateTime;

@Component
@RequiredArgsConstructor
public class MessageMapper {
    private final ConvertDateTime convertDateTime;
    public MessageResponse convertToMessageResponse(Messages message){

        MessageResponse.MessageResponseBuilder messageResponseBuilder =  MessageResponse.builder()
                .id(message.getId())
                .messageType(message.getType())
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getFullName())
                .content(message.getContent())
                .mediaUrl(message.getMediaUrl())
                .mediaType(message.getMediaType())
                .sentTime(this.convertDateTime.convertInstant(message.getCreatedAt()))
                .conversationId(message.getConversation().getId());
        if(message.getParentMessage() !=null){
            messageResponseBuilder.parentId(message.getParentMessage().getId());
            messageResponseBuilder.parentMessageSenderName(message.getParentMessage().getSender().getFullName());
            messageResponseBuilder.parentMessageContent(message.getParentMessage().getContent());
            messageResponseBuilder.parentMessageType(message.getParentMessage().getType());
            messageResponseBuilder.parentMediaType(message.getParentMessage().getMediaType());
        }
        return messageResponseBuilder.build();
    }

    public LastMessageResponse convertToLastMessageResponse(Messages message){
        return LastMessageResponse.builder()
                .content(message.getContent())
                .messageType(message.getType())
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getFullName())
                .mediaType(message.getMediaType())
                .lastTime(this.convertDateTime.convertInstant(message.getCreatedAt()))
                .build();
    }
}
