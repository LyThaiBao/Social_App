package social_app.example.social_app.entity;


import jakarta.persistence.*;
import lombok.*;
import social_app.example.social_app.type.ConversationType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conversations")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
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

    @Builder.Default
    @OneToMany(mappedBy = "conversation",fetch = FetchType.LAZY)
    private List<Participants> participantsList = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "conversation",fetch = FetchType.LAZY)
    private List<Messages> messages = new ArrayList<>();
}
