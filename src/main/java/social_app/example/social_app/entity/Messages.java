package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import social_app.example.social_app.type.MediaType;
import social_app.example.social_app.type.MessageType;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "messages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Messages {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    @Builder.Default
    private MessageType type = MessageType.TEXT; // default is type Text

    @Column(name = "mediaUrl")
    private String mediaUrl;

    @Column(name = "mediaType")
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private Conversations conversation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender")
    private Members sender;



    // case this msg is reply msg
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Messages parentMessage;

    //case this msg is root msg
    @OneToMany(mappedBy = "parentMessage",fetch = FetchType.LAZY)
    @Builder.Default
    private List<Messages> replyMessages = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at" ,nullable = false)
    private Instant createdAt;

}
