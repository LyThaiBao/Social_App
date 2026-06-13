package social_app.example.social_app.service.comment;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.comment.CommentRequest;
import social_app.example.social_app.dto.comment.CommentResponse;
import social_app.example.social_app.entity.Comments;
import social_app.example.social_app.entity.Posts;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.AuthException;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.mapper.CommentMapper;
import social_app.example.social_app.repo.CommentRepository;
import social_app.example.social_app.service.post.PostService;
import social_app.example.social_app.service.usr.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService{
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;
    private final CommentMapper commentMapper;
    @Override
    public CommentResponse createComment(CommentRequest request, Principal principal) {
        String currentUsername = principal.getName();
        if(currentUsername==null){
            throw new AuthException("You do not have permission");
        }
        Users user = this.userService.findByUsername(currentUsername);
        Posts post = this.postService.getPostEntity(request.getPostId());
        Comments comment = Comments.builder()
                .member(user.getMember())
                .post(post)
                .content(request.getContent())
                .build();
        this.commentRepository.save(comment);
        return this.commentMapper.convertToCommentResponse(comment);
    }

    @Override
    @Transactional
    public String deleteComment(Integer postId,Integer commentId,Principal principal) {
        String currentUsername = principal.getName();
        if(currentUsername==null){
            throw new AuthException("You do not have permission");
        }
        Users user = this.userService.findByUsername(currentUsername);
        Comments comment = this.commentRepository.findById(commentId).orElseThrow(()-> new NotFoundResource("Not found comment"));
        if(!Objects.equals(comment.getMember().getId(), user.getMember().getId())){
            throw new AuthException("You do not have permission");
        }
        int result = this.commentRepository.deleteComment(postId,commentId);
        if(result == 0){
            throw new NotFoundResource("Not found comment");
        }
        return "Delete success comment with id"+commentId;
    }

    @Override
    public List<CommentResponse> getAllComments(Integer postId) {
       List<Comments> commentsList =  this.commentRepository.getAllByPostId(postId);
       return commentsList.stream().map(this.commentMapper::convertToCommentResponse).toList();
    }
}
