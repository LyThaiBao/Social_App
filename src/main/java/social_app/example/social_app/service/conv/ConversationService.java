package social_app.example.social_app.service.conv;

import social_app.example.social_app.dto.conv.ConversationResponse;
import social_app.example.social_app.entity.Conversations;

import java.security.Principal;
import java.util.List;

public interface ConversationService {
    Conversations createPrivateConversation(Integer currentMemberId,Integer partnerId);
    Conversations getConversationEntityById(Integer id);
    List<ConversationResponse> getConversations(Principal principal);
    ConversationResponse getConversation(Integer id,Principal principal);
    String resolveConversationName(Conversations conversation, Integer currentMemberId);
    ConversationResponse findOrCreatePrivateConversation(Principal principal, Integer partnerId);
    }
