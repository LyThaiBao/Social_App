package social_app.example.social_app.dto.typing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypingResponse {
    private Integer senderId;
    private String memberName;
}
