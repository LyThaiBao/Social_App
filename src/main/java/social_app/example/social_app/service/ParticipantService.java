package social_app.example.social_app.service;

import social_app.example.social_app.dto.ParticipantRequest;
import social_app.example.social_app.dto.ParticipantResponse;

import java.security.Principal;

public interface ParticipantService {
    ParticipantResponse createParticipant(ParticipantRequest request);
    ParticipantResponse deleteParticipantById(Integer conversationId, Principal principal);
}
