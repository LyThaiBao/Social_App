package social_app.example.social_app.service.post;

import org.springframework.data.domain.Page;
import social_app.example.social_app.dto.post.PostRequest;
import social_app.example.social_app.dto.post.PostResponse;

import java.security.Principal;
import java.util.List;

public interface PostService {
    PostResponse createPost(PostRequest request,Principal principal);
    Page<PostResponse> getNewPosts(Principal principal, int pageNum, int size);
}
