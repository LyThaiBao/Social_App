package social_app.example.social_app.mapper;

import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.ParticipantResponse;
import social_app.example.social_app.entity.Participants;

@Component
public class ParticipantMapper {

    public ParticipantResponse convertToParticipantResponse(Participants participant){
        return ParticipantResponse.builder()
                .memberId(participant.getMember().getId())
                .conversationId(participant.getConversation().getId())
                .build();
    }
}
