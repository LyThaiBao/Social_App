package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.type.LikeStatus;

import java.time.Instant;

@Entity
@Table(name = "likes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Likes {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Members member;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Posts post;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private LikeStatus status = LikeStatus.UNLIKE;

    @Column(name = "create_at")
    private Instant createAt;

}
