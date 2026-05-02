package social_app.example.social_app.service.auth;

import social_app.example.social_app.dto.auth.LoginRequest;
import social_app.example.social_app.dto.auth.LoginResponse;
import social_app.example.social_app.dto.auth.RegisterDTO;
import social_app.example.social_app.dto.usrAndMember.UserResponse;

public interface AuthService {
     UserResponse register(RegisterDTO registerInFo);
     LoginResponse login(LoginRequest request);
}
