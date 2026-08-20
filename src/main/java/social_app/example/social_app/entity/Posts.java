package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import social_app.example.social_app.type.MediaType;
import social_app.example.social_app.type.PostStatus;

import java.time.Instant;

@Entity
@Table(name = "posts")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Members member;

    @Column(name = "content")
    private String content;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "media_type")
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @Column(name = "status")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.PUBLIC;

    @Column(name = "total_like")
    @Builder.Default
    private Long totalLikes = 0L;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;
}
