package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import social_app.example.social_app.type.LikeStatus;

import java.time.Instant;

@Entity
@Table(name = "likes",indexes = {@Index(name = "idx_member_post_like",columnList = "member_id,post_id",unique = true)})
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Likes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Members member;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Posts post;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private LikeStatus status = LikeStatus.UNLIKE;

    @Column(name = "created_at")
    @CreationTimestamp
    private Instant createdAt;
}
