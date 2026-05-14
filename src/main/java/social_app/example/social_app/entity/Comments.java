package social_app.example.social_app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "posts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Members member;

    @JoinColumn(name = "post_id")
    @ManyToOne
    private Posts post;

    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private Instant createAt;


}
