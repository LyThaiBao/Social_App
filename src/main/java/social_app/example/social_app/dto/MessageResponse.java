package social_app.example.social_app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.entity.MessageType;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageResponse {
    private Integer id;
    private String content;
    private String mediaUrl;
    private Integer conversationId;
    private Integer senderId;
    private String senderName;
    private MessageType messageType;
    private String sentTime;
    private Integer parentId;
    private String parentMessageContent;
    private String parentMessageSenderName;
    private MessageType parentMediaType;
}
