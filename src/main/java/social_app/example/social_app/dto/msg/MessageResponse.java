package social_app.example.social_app.dto.msg;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.type.MediaType;
import social_app.example.social_app.type.MessageType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageResponse {
    private Integer id;
    private String content;
    private MessageType messageType;
    private String mediaUrl;
    private MediaType mediaType;
    private Integer conversationId;
    private Integer senderId;
    private String senderName;
    private String sentTime;
    private Integer parentId;
    private String parentMessageContent;
    private MessageType parentMessageType;
    private String parentMessageSenderName;
    private MediaType parentMediaType;
}
