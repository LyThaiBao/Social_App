package social_app.example.social_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse <T>{
    private String message;
    private boolean isSuccess;
    private T body;

    public static <T> ApiResponse<T> success(String message,T data){
        return ApiResponse.<T>builder()
                .isSuccess(true)
                .body(data)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> error(String message){
        return ApiResponse.<T>builder()
                .isSuccess(false)
                .body(null)
                .message(message)
                .build();
    }
}
