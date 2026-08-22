package social_app.example.social_app.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginResponse {
    private Integer memberId;
    private String accessToken;
    private String refreshToken;
    private List<String> roles;
    private String fullName;
}
