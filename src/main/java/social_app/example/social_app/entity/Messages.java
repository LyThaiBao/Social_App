package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "messages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Messages {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "mediaUrl")
    private String mediaUrl;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversations conversation;

    @ManyToOne
    @JoinColumn(name = "sender")
    private Members sender;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType type = MessageType.TEXT; // default is type TEXT

    @CreationTimestamp
    @Column(name = "created_at" ,nullable = false)
    private Instant createdAt;

}
