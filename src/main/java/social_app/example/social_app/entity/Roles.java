package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private  Integer id;

    @Column(name = "role_name")
    private  String roleName;

    @CreationTimestamp
    @Column(name = "created_at")
    private Instant createdAt;

}
