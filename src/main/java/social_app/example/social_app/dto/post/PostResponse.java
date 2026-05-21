package social_app.example.social_app.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.type.PostStatus;

import java.time.Instant;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostResponse {
    private Integer id;
    private Integer memberId;
    private String memberName;
    private String content;
    private String mediaUrl;
    private Long totalLikes;
    private boolean isLiked;
    private PostStatus status;
    private Instant createAt;
}
