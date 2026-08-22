package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.RefreshToken;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    @Query("select r from RefreshToken r join fetch r.users where r.refreshToken =:refreshToken")
    Optional<RefreshToken> getRefreshToken(@Param("refreshToken") String refreshToken);

    Optional<RefreshToken> getRefreshTokenByUsersId(Integer usersId);

    @Modifying
    @Query("delete from RefreshToken r where r.users.id = :userId")
    int deleteByUserId(@Param("userId") Integer userId);
}
