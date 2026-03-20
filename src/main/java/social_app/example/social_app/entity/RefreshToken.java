package social_app.example.social_app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Users users;
}
