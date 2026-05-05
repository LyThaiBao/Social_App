package social_app.example.social_app.dto.notification;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendRequest {
    private Integer senderId;
    private String senderName;
    
}
