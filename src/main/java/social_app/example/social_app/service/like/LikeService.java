package social_app.example.social_app.service.like;

import social_app.example.social_app.dto.like.LikeResponse;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface LikeService {
    LikeResponse toggleLike(Integer postId, Principal principal);

}
