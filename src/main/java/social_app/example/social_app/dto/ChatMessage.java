package social_app.example.social_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.entity.MessageType;

import java.sql.Timestamp;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessage {
    public enum ChatType{
        CHAT,JOIN,LEAVE
    }
    private String content;
    private Integer senderId;
//    private Integer recipientId;
    private Integer conversationId;
    private ChatType type;
    private MessageType messageType;
    private Timestamp timestamp;
}
