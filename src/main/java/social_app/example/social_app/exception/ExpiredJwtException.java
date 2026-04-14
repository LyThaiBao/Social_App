package social_app.example.social_app.exception;

public class ExpiredJwtException extends RuntimeException{
    public ExpiredJwtException(String message){
        super(message);
    }
}
