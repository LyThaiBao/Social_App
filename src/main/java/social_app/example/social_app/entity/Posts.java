package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.*;
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

    @Column(name = "status")
    @Enumerated
    private PostStatus status;

    @Column(name = "create_at")
    private Instant createAt;

    @PrePersist
    protected void onCreate(){
        this.createAt = Instant.now();
    }
}
