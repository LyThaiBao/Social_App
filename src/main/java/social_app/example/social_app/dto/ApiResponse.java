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
    private Integer status;
    private String message;
    private T body;
}
