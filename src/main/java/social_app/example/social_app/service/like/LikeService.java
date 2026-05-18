package social_app.example.social_app.service.like;

import social_app.example.social_app.dto.like.LikeToggleResponse;

import java.security.Principal;

public interface LikeService {
    LikeToggleResponse toggleLike(Integer postId, Principal principal);
}
