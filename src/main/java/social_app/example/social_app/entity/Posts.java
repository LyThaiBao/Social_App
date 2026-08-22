package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.*;
import social_app.example.social_app.type.MediaType;
import social_app.example.social_app.type.PostStatus;

import java.time.Instant;

@Entity
@Table(name = "posts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Members member;

    @Column(name = "content")
    private String content;

    @Column(name = "media_url")
    private String mediaUrl;

    @Column(name = "media_type")
    private MediaType mediaType;

    @Column(name = "status")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.PUBLIC;

    @Column(name = "total_like")
    @Builder.Default
    private Long totalLikes = 0L;

    @Column(name = "create_at")
    private Instant createdAt;

    @PrePersist
    protected void onCreate(){
        this.createdAt = Instant.now();
    }
}
