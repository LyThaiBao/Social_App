package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import social_app.example.social_app.entity.Notification;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
    @Query("select n from Notification n where n.recipient.id = :id order by n.createdAt desc")
    List<Notification> findAllByRecipientId(Integer id);

    @Query("select count(n) from Notification n where n.recipient.id = :recipientId and n.isRead = false")
    int countUnreadNotification(@Param("recipientId") Integer recipientId);

    @Modifying
    @Query("update Notification n set n.isRead = true where n.recipient.id =:recipientId")
    int markRead(@Param("recipientId") Integer recipientId);

    @Modifying
    @Query("update Notification n set n.isDeleted = true where n.id = :notifId")
    int delete(@Param("notifId") Integer notifId);
}
