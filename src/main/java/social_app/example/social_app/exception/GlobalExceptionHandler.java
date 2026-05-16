package social_app.example.social_app.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import social_app.example.social_app.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Loi trung data trong DB
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicate(DataIntegrityViolationException dataIntegrityViolationException){
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(ApiResponse.error(dataIntegrityViolationException.getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntime(AuthException authException){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error(authException.getMessage()));
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiResponse<String>> handleExpiredToken(ExpiredJwtException jwtException){
        return  ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error("Token het han hoac khong chinh xac"));
    }

    @ExceptionHandler(SentFriendShipException.class)
    public ResponseEntity<ApiResponse<Void>> handleSentFriendShip(SentFriendShipException friendShipException){
        return ResponseEntity.badRequest().body(ApiResponse.error(friendShipException.getMessage()));
    }


    @ExceptionHandler(NotFoundResource.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundResource(NotFoundResource notFoundResource){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(notFoundResource.getMessage()));
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ApiResponse<String>> handleForbidden(ForbiddenException forbiddenException){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiResponse.error(forbiddenException.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> unDefineEx(Exception e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.error("Loi khong xac dinh"));
    }


}
