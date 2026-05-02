package social_app.example.social_app.dto.auth;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginRequest {
    private  String username;
    private String password;
}
