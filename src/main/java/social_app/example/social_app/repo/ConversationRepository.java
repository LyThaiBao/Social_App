package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import social_app.example.social_app.entity.Conversations;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversations,Integer> {

}
