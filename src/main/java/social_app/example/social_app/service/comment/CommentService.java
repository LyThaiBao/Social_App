package social_app.example.social_app.service.comment;

import social_app.example.social_app.dto.comment.CommentRequest;
import social_app.example.social_app.dto.comment.CommentResponse;

import java.security.Principal;
import java.util.List;

public interface CommentService {
    CommentResponse createComment(CommentRequest request, Principal principal);
    String deleteComment(Integer postId,Integer commentId,Principal principal);
    List<CommentResponse> getAllComments(Integer postId);
}
