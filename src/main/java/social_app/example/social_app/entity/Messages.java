package social_app.example.social_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import social_app.example.social_app.type.MediaType;
import social_app.example.social_app.type.MessageType;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "message_type")
    private MessageType type = MessageType.TEXT; // default is type File

    @Column(name = "mediaUrl")
    private String mediaUrl;

    @Column(name = "mediaType")
    @Enumerated(EnumType.STRING)
    private MediaType mediaType;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversations conversation;

    @ManyToOne
    @JoinColumn(name = "sender")
    private Members sender;



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
