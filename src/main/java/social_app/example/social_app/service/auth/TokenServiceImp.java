package social_app.example.social_app.service.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import social_app.example.social_app.entity.RefreshToken;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.repo.RefreshTokenRepository;


@Slf4j
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
    @Transactional
    public RefreshToken save(RefreshToken refreshToken) {
          this.refreshTokenRepository.deleteByUserId(refreshToken.getUsers().getId());
        log.info(">>>SAVE: ");
        return this.refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void remove(RefreshToken refreshToken) {
        this.refreshTokenRepository.delete(refreshToken);
    }
}
