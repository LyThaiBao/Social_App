package social_app.example.social_app.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_token")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RefreshToken {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expired")
    private Instant expired;

    @OneToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private Users users;
}
