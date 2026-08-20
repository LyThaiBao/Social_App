package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "participants",indexes = {@Index(name = "idx_conversation_member",columnList = "conversation_id, member_id",unique = true)})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Members member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversations conversation;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
}
