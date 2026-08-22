package social_app.example.social_app.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private  String SECRET_KEY;


    // Hàm tạo SecretKey từ chuỗi String có từ bản 0.12.0
    private SecretKey getSigningKey() {
        byte[] keyBytes = this.SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String username, long JwtExpiration){
        return Jwts.builder()
                .signWith(this.getSigningKey())
                .subject(username)
                .expiration(new Date(System.currentTimeMillis()+JwtExpiration))
                .issuedAt(new java.util.Date())
                .compact();
    }


    public String extractUsername(String token){
           return Jwts.parser()
                   .verifyWith(this.getSigningKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getSubject();
    }

    public boolean isValidateToken(String token){
        try{
            Jwts.parser().verifyWith(this.getSigningKey()).build().parseSignedClaims(token);
            return true;
        }
        catch(Exception e){
                return false;
        }
    }
}
