package social_app.example.social_app.service.like;

import social_app.example.social_app.dto.like.LikeResponse;

import java.security.Principal;

public interface LikeService {
    LikeResponse toggleLike(Integer postId, Principal principal);
    LikeResponse getLikesOfPost(Integer postId,Principal principal);
}
