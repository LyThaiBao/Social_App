package social_app.example.social_app.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class LastMessageResponse {
    private String content;
//    private LocalDate lastTime;
}
