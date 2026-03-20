package social_app.example.social_app.service;

import social_app.example.social_app.dto.LoginRequest;
import social_app.example.social_app.dto.LoginResponse;
import social_app.example.social_app.dto.RegisterDTO;
import social_app.example.social_app.dto.UserResponse;

public interface AuthService {
     UserResponse register(RegisterDTO registerInFo);
     LoginResponse login(LoginRequest request);
}
