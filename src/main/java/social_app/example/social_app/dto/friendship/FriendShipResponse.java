package social_app.example.social_app.dto.friendship;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FriendShipResponse {
    private Integer senderId;
    private Integer recipientId;
    private String senderName;
    private String statusText;
    @CreationTimestamp
    private Instant createdAt;

    private String message;
}
