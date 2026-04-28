package social_app.example.social_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.entity.MessageType;
import social_app.example.social_app.entity.Messages;

import java.sql.Timestamp;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessage {
    public enum ChatType{
        CHAT,JOIN,LEAVE
    }
    private String content; // *
    private String mediaUrl;
    private Integer senderId;// *
//    private Integer recipientId;
    private Integer conversationId;// *
    private ChatType type;
    private MessageType messageType;
    private Integer parentMessageId;
}
