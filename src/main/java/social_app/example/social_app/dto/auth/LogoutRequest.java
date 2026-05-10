package social_app.example.social_app.dto.auth;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutRequest {
    private String refreshToken;
}
