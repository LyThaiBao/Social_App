package social_app.example.social_app.dto;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.context.annotation.Bean;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class FriendShipResponse {
    private String statusText;
    private Integer status;
    @CreationTimestamp
    private Instant createdAt;

    private String message;
}
