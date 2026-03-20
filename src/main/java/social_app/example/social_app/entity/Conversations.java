package social_app.example.social_app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "conversations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Conversations {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "conversation_type",length = 10,nullable = false)
    private ConversationType type;


}
