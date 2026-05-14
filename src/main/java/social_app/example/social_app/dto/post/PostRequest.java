package social_app.example.social_app.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.type.PostStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostRequest {
    private Integer memberId;
    private String content;
    private String mediaUrl;
    private PostStatus status;
}
