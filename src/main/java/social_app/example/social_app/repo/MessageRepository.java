package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.Messages;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Messages,Integer> {
    List<Messages> getMessagesByConversationId(Integer conversationId);
}
