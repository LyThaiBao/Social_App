package social_app.example.social_app.service;

import social_app.example.social_app.entity.Conversations;

import java.util.Optional;

public interface ConversationService {
    Optional<Conversations> getConversationById(Integer id);
}
