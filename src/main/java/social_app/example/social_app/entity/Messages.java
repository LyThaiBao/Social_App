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
@Table(name = "messages")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Messages {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "content")
    private String content;

    @Column(name = "mediaUrl")
    private String mediaUrl;

    @Column(name = "mediaType")
    private MessageType mediaType;
    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversations conversation;

    @ManyToOne
    @JoinColumn(name = "sender")
    private Members sender;

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType type = MessageType.FILE; // default is type File

    // case this msg is reply msg
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Messages parentMessage;

    //case this msg is root msg
    @OneToMany(mappedBy = "parentMessage")
    private List<Messages> replyMessages;

    @CreationTimestamp
    @Column(name = "created_at" ,nullable = false)
    private Instant createdAt;

}
