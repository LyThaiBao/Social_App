package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.Messages;

@Repository
public interface MessageRepository extends JpaRepository<Messages,Integer> {
}
