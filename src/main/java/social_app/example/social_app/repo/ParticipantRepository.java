package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.Participants;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participants,Integer> {
    Optional<Participants> findByMemberIdAndConversationId(Integer member_id, Integer conversation_id);
}
