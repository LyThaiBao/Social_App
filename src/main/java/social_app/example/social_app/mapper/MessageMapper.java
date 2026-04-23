package social_app.example.social_app.mapper;

import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.LastMessageResponse;
import social_app.example.social_app.dto.MessageResponse;
import social_app.example.social_app.entity.Messages;

import java.util.List;

@Component
public class MessageMapper {

    public MessageResponse convertToMessageResponse(Messages message){

        return MessageResponse.builder()
                .messageType(message.getType())
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getFullName())
                .content(message.getContent())
                .conversationId(message.getConversation().getId())
                .build();
    }

    public LastMessageResponse convertToLastMessageResponse(Messages message){
        return LastMessageResponse.builder()
                .content(message.getContent())
                .build();
    }
}
