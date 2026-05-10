package social_app.example.social_app.service.auth;

import social_app.example.social_app.entity.RefreshToken;

import java.util.Optional;

public interface TokenService {

    RefreshToken getRefreshToken(String refreshToken);
    RefreshToken getRefreshTokenByUserId(Integer userId);
    RefreshToken save(RefreshToken refreshToken);
    void remove(RefreshToken refreshToken);
}
