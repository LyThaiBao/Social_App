package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name="friendShips",indexes = {@Index(name = "idx_requester_addresser",columnList = "requester_id,addresser_id",unique = true)})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FriendShips {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Members requester;

    @ManyToOne
    @JoinColumn(name = "addresser_id")
    private Members addresser;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",length = 15,nullable = false)
    private FriendShipType status;

    @CreationTimestamp
    @Column(name = "create_at",nullable = false)
    private Instant createAt;
}
