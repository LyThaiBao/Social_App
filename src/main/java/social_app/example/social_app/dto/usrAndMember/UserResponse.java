package social_app.example.social_app.dto.usrAndMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.entity.Users;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponse {
    private String username;
    private Instant createdAt;

    private UserResponse convertToDTO(Users users){
        return UserResponse.
                builder()
                .username(users.getUsername())
                .createdAt(users.getCreatedAt())
                .build();
    }
}
