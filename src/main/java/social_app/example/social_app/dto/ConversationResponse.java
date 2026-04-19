package social_app.example.social_app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.entity.ConversationType;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversationResponse {
    private ConversationType type;
    private Integer conversationId;
    private String conversationName; // Tên nhóm hoặc tên người kia
    //private String conversationAvatar; // Avatar nhóm hoặc avatar người kia
    //private String lastMessage; //Để hiển thị tin nhắn cuối cùng
    //private Integer partnerId; // ID người kia (để click vào là điều hướng tới profile)
}