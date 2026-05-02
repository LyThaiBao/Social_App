package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import social_app.example.social_app.entity.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    List<Notification> findAllByRecipientId(Integer id);
}
