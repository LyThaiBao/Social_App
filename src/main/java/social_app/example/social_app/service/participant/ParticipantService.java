package social_app.example.social_app.service.participant;

import social_app.example.social_app.dto.participant.ParticipantResponse;
import social_app.example.social_app.entity.Conversations;
import social_app.example.social_app.entity.Participants;

import java.security.Principal;
import java.util.List;

public interface ParticipantService {
    ParticipantResponse createParticipant(Integer memberId, Conversations conversation);
    ParticipantResponse deleteParticipantById(Integer conversationId, Principal principal);
    List<Participants> getAllParticipant(Principal principal);// truyen principal de tu lay cua no thoi
    List<Participants> getByConversationId(Integer conversationId);
}
