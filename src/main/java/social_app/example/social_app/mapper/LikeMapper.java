package social_app.example.social_app.mapper;

import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.like.LikeResponse;
import social_app.example.social_app.entity.Likes;
import social_app.example.social_app.type.LikeStatus;

@Component
public class LikeMapper {

    public LikeResponse convertToLikeResponse(Likes like){
        return LikeResponse.builder()
                .liked(like.getStatus()== LikeStatus.LIKED)
                .postId(like.getPost().getId())
                .totalLikeOfPost(like.getPost().getTotalLikes())
                .build();
    }
}
