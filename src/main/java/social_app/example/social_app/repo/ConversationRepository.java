package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import social_app.example.social_app.entity.Conversations;

import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversations,Integer> {

}
