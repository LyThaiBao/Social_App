package social_app.example.social_app.mapper;

import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.conv.ConversationResponse;
import social_app.example.social_app.entity.Conversations;

@Component
public class ConversationMapper {

    public  ConversationResponse convertToConversationResponse(Conversations conversation){
        return  ConversationResponse.builder()
                .conversationName(conversation.getConversationName())
                .type(conversation.getType())
                .conversationId(conversation.getId())
                .build();
    }
}
