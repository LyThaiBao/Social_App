package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_name")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "enable")
    private boolean enable;
    @CreationTimestamp
    @Column(name = "create_at")
    private Instant createdAt;

    @OneToOne(mappedBy = "user")
    private Members member;

    @OneToMany(mappedBy = "user",fetch =FetchType.EAGER)
    private List<UserRoles> userRoles;
}
