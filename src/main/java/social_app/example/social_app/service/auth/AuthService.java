package social_app.example.social_app.service.auth;

import social_app.example.social_app.dto.auth.*;
import social_app.example.social_app.dto.usrAndMember.UserResponse;

import java.security.Principal;

public interface AuthService {
     UserResponse register(RegisterDTO registerInFo);
     LoginResponse login(LoginRequest request);
     LogoutResponse logout(String refreshToken);
     RefreshTokenResp refreshToken(RefreshTokenRequ request);
}
