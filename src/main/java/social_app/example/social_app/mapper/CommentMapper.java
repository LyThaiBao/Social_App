package social_app.example.social_app.mapper;

import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.comment.CommentResponse;
import social_app.example.social_app.entity.Comments;

@Component
public class CommentMapper {

    public CommentResponse convertToCommentResponse(Comments comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .memberId(comment.getMember().getId())
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .isDeleted(comment.isDeleted())
                .createdAt(comment.getCreateAt())
                .build();
    }
}
