package social_app.example.social_app.mapper;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.auth.RegisterDTO;
import social_app.example.social_app.dto.usrAndMember.UserResponse;
import social_app.example.social_app.entity.Users;


@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    public UserResponse convertToResponse(Users users) {
        return UserResponse
                .builder()
                .username(users.getUsername())
                .createdAt(users.getCreatedAt())
                .build();
    }

    public Users convertToUser(RegisterDTO registerInFo){
        return Users
                .builder()
                .enable(true)
                .username(registerInFo.getUsername())
                .password(this.passwordEncoder.encode(registerInFo.getPassword()))
                .build();
    }
}
