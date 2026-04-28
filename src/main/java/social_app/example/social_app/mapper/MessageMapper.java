package social_app.example.social_app.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.LastMessageResponse;
import social_app.example.social_app.dto.MessageResponse;
import social_app.example.social_app.entity.Messages;
import social_app.example.social_app.util.ConvertDateTime;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageMapper {
    private final ConvertDateTime convertDateTime;
    public MessageResponse convertToMessageResponse(Messages message){

        MessageResponse.MessageResponseBuilder messageResponseBuilder =  MessageResponse.builder()
                .id(message.getId())
                .messageType(message.getMediaType())
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getFullName())
                .content(message.getContent())
                .mediaUrl(message.getMediaUrl())
                .sentTime(this.convertDateTime.convertInstant(message.getCreatedAt()))
                .conversationId(message.getConversation().getId());
        if(message.getParentMessage() !=null){
            messageResponseBuilder.parentId(message.getParentMessage().getId());
            messageResponseBuilder.parentMessageSenderName(message.getParentMessage().getSender().getFullName());
            messageResponseBuilder.parentMessageContent(message.getParentMessage().getContent());
            messageResponseBuilder.parentMediaType(message.getParentMessage().getMediaType());
        }
        return messageResponseBuilder.build();
    }

    public LastMessageResponse convertToLastMessageResponse(Messages message){
        return LastMessageResponse.builder()
                .content(message.getContent())
                .messageType(message.getMediaType())
                .lastTime(this.convertDateTime.convertInstant(message.getCreatedAt()))
                .build();
    }
}
