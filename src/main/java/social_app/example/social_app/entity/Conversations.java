package social_app.example.social_app.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.type.ConversationType;

import java.util.List;

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

    @Column(name = "name")
    private String conversationName;

    @OneToMany(mappedBy = "conversation",fetch = FetchType.EAGER)
    private List<Participants> participantsList;

    @OneToMany(mappedBy = "conversation")
    private List<Messages> messages;

}
