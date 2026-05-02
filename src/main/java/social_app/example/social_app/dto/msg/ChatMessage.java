package social_app.example.social_app.dto.msg;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.type.ChatType;
import social_app.example.social_app.type.MediaType;
import social_app.example.social_app.type.MessageType;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChatMessage {
    private String content; // *
    private String mediaUrl;
    private MediaType mediaType;
    private Integer senderId;// *
    // private Integer recipientId;
    private Integer conversationId;// *
    private ChatType type;
    private MessageType messageType;
    // use for reply feature
    private Integer parentMessageId;
}
