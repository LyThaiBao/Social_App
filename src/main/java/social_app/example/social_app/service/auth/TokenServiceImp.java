package social_app.example.social_app.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.entity.RefreshToken;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.repo.RefreshTokenRepository;


@Service
@RequiredArgsConstructor
public class TokenServiceImp implements TokenService{
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public RefreshToken getRefreshToken(String refreshToken) {
        return this.refreshTokenRepository.getRefreshTokenByRefreshToken(refreshToken).orElseThrow(()->new NotFoundResource("Not found refresh token"));
    }

    @Override
    public RefreshToken getRefreshTokenByUserId(Integer userId) {
        return this.refreshTokenRepository.getRefreshTokenByUsersId(userId).orElseThrow(()-> new NotFoundResource("Not found token"));
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return this.refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void remove(RefreshToken refreshToken) {
        this.refreshTokenRepository.delete(refreshToken);
    }
}
