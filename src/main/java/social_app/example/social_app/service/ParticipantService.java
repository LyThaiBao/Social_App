package social_app.example.social_app.service;

import social_app.example.social_app.dto.ParticipantRequest;
import social_app.example.social_app.dto.ParticipantResponse;
import social_app.example.social_app.entity.Participants;

import java.security.Principal;
import java.util.List;

public interface ParticipantService {
//    ParticipantResponse createParticipant(Integer memberId,Integer conversationId);
    ParticipantResponse deleteParticipantById(Integer conversationId, Principal principal);
    List<Participants> getAllParticipant(Principal principal);
}
