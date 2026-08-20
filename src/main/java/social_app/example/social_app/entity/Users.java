package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_name",unique = true)
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "enable")
    private boolean enable;

    @Column(name = "create_at")
    private Instant createdAt;

    @ToString.Exclude
    @OneToOne(mappedBy = "user")
    private Members member;

    @Builder.Default
    @OneToMany(mappedBy = "user",fetch =FetchType.LAZY)
    private List<UserRoles> userRoles = new ArrayList<>();
}
