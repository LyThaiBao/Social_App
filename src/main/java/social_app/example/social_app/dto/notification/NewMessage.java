package social_app.example.social_app.dto.notification;


import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class NewMessage{
   private Integer senderId;
   private String content;
   private Integer conversationId;

}
