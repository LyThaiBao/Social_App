package social_app.example.social_app.service.post;

import org.springframework.data.domain.Page;
import social_app.example.social_app.dto.post.PostRequest;
import social_app.example.social_app.dto.post.PostResponse;
import social_app.example.social_app.entity.Posts;

import java.security.Principal;


public interface PostService {
    PostResponse createPost(PostRequest request,Principal principal);
    Page<PostResponse> getNewPosts(Principal principal, int pageNum, int size);
    void deletePost(Integer id);
    PostResponse modifyPost(Integer id,PostRequest request);

    Posts getPostEntity(Integer id);
}
