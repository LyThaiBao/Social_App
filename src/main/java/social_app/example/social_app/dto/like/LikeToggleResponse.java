package social_app.example.social_app.dto.like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeToggleResponse {
    private boolean liked;
    private Long totalLikeOfPost;
}
