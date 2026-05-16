package social_app.example.social_app.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class JwtUtil {
    private String SECRET_KEY = "lythaibaotimyssfsfjfjsjfksdjfkdsfksdjfkdjfkdsjfksjfwerrerfsdfsdf";

    public String createToken(String username,long JwtExpiration){
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+JwtExpiration))
                .setIssuedAt(new java.util.Date())
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();

    }


    public String extractUsername(String token){
           return Jwts.parser()
                   .setSigningKey(SECRET_KEY)
                   .parseClaimsJws(token)
                   .getBody()
                   .getSubject();

    }

    public boolean isValidateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        }
        catch(Exception e){
                return false;
        }
    }
}
