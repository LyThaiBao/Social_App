package social_app.example.social_app.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import social_app.example.social_app.dto.ApiResponse;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // Loi trung data trong DB
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleDuplicate(DataIntegrityViolationException dataIntegrityViolationException){
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .status(4009) // 4009 conflict data
                .message("Data Conflict")
                .body("Du lieu da ton tai trong he thong")
                .build();

        return  ResponseEntity.status(HttpStatus.CONFLICT).body(apiResponse);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ApiResponse<String>> handleRuntime(AuthException authException){
        ApiResponse<String> apiResponse = ApiResponse.<String>builder()
                .status(4001)
                .message("Kiem tra la thong tin dang nhap")
                .body("Sai Thong Tin Dang Nhap")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiResponse);
    }



}
