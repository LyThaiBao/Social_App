package social_app.example.social_app.exception;


public class NotFoundResource extends RuntimeException {
    public NotFoundResource(String message){
        super(message);
    }
}
