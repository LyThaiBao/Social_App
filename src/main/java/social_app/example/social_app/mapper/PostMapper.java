package social_app.example.social_app.mapper;

import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.post.PostResponse;
import social_app.example.social_app.entity.Posts;

@Component
public class PostMapper {

    public PostResponse convertToPostResponse(Posts post){
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .mediaUrl(post.getMediaUrl())
                .memberId(post.getMember().getId())
                .status(post.getStatus())
                .createAt(post.getCreateAt())
                .build();
    }
}
