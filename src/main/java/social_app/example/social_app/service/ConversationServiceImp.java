package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.entity.Conversations;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.repo.ConversationRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationServiceImp implements ConversationService{
    private  final ConversationRepository conversationRepository;

    @Override
    public Conversations getConversationById(Integer id) {
       return this.conversationRepository.findById(id).orElseThrow(()-> new NotFoundResource("Not found conversation"));

    }
}
