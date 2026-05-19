package social_app.example.social_app.service.like;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.like.LikeResponse;
import social_app.example.social_app.entity.Likes;
import social_app.example.social_app.entity.Posts;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.repo.LikeRepository;
import social_app.example.social_app.service.post.PostService;
import social_app.example.social_app.service.usr.UserService;
import social_app.example.social_app.type.LikeStatus;

import java.security.Principal;


@Service
@RequiredArgsConstructor
public class LikeServiceImp implements LikeService{
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;
    @Override
    @Transactional
    public LikeResponse toggleLike(Integer postId, Principal principal) {
        String currentUsername =  principal.getName();
        Users user = this.userService.findByUsername(currentUsername);
        Posts post = this.postService.getPostEntity(postId);
        Likes like = this.likeRepository.getLikeByMemberIdAndPostId(user.getMember().getId(),postId);
        if(like!=null){
            like.setStatus(like.getStatus() == LikeStatus.UNLIKE?LikeStatus.LIKED:LikeStatus.UNLIKE);
        }
        else{
            like = Likes.builder()
                    .member(user.getMember())
                    .status(LikeStatus.LIKED)
                    .post(post)
                    .build();
        }
        this.likeRepository.save(like);
        Long totalLikes = this.likeRepository.countLikeOfPost(postId);
        return LikeResponse.builder()
                .liked(like.getStatus() == LikeStatus.LIKED)
                .postId(postId)
                .totalLikeOfPost(totalLikes)
                .build();
    }

    @Override
    public LikeResponse getLikesOfPost(Integer postId,Principal principal) {
        String currentUsername =  principal.getName();
        Users user = this.userService.findByUsername(currentUsername);
        Likes like = this.likeRepository.getLikeByMemberIdAndPostId(user.getMember().getId(),postId);
        Long totalLikes = this.likeRepository.countLikeOfPost(postId);
        return LikeResponse.builder()
                .postId(postId)
                .totalLikeOfPost(totalLikes)
                .liked(like != null && like.getStatus() == LikeStatus.LIKED)
                .build();
    }
}
