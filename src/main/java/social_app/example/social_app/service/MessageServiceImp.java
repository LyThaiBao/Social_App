package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.entity.Messages;
import social_app.example.social_app.repo.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageServiceImp implements MessageService{
    private final MessageRepository messageRepository;
    @Override
    public Messages save(Messages messages) {
        return this.messageRepository.save(messages);
    }
}
