package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.*;

import social_app.example.social_app.type.NotificationType;
import java.time.Instant;

@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    // Người nhận thông báo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    @ToString.Exclude
    private Members recipient;

    // Người gửi/tác nhân gây ra thông báo (có thể null nếu là thông báo hệ thống)
    @Column(name = "sender_id")
    private Integer senderId;

    @Column(name = "sender_name")
    private String senderName;

    // ID của đối tượng liên quan (Post ID, Comment ID, Friendship ID...)
    @Column(name = "reference_id")
    private Integer referenceId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_read")
    private boolean isRead = false;

    @Column(name = "created_at")
    private Instant createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }
}