package social_app.example.social_app.dto.comment;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class CommentResponse {
    private Integer id;
    private Integer memberId;
    private String memberName;
    private Integer postId;
    private String content;
    private boolean isDeleted;
    private Instant createdAt;
}
