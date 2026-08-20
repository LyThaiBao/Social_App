package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import social_app.example.social_app.type.FriendShipType;

import java.time.Instant;

@Entity
@Table(name="friend_ships",indexes = {@Index(name = "idx_requester_addresser",columnList = "requester_id,addresser_id",unique = true)})
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendShips {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requester_id")
    private Members requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addresser_id")
    private Members addresser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",length = 15,nullable = false)
    @Builder.Default
    private FriendShipType status = FriendShipType.PENDING;

    @CreationTimestamp // auto generate
    @Column(name = "created_at",nullable = false)
    private Instant createdAt;
}
