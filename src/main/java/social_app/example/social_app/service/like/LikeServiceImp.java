package social_app.example.social_app.service.like;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.like.LikeToggleResponse;
import social_app.example.social_app.entity.Likes;
import social_app.example.social_app.entity.Posts;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.repo.LikeRepository;
import social_app.example.social_app.service.post.PostService;
import social_app.example.social_app.service.usr.UserService;
import social_app.example.social_app.type.LikeStatus;

import java.security.Principal;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class LikeServiceImp implements LikeService{
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;
    @Override
    @Transactional
    public LikeToggleResponse toggleLike(Integer postId, Principal principal) {
        String currentUsername =  principal.getName();
        Users user = this.userService.findByUsername(currentUsername);
        Posts post = this.postService.getPostEntity(postId);
        Likes like = this.likeRepository.getLikeByMemberId(user.getMember().getId());
        if(like!=null){
            like.setStatus(Objects.equals(like.getStatus(), LikeStatus.UNLIKE)?LikeStatus.LIKED:LikeStatus.UNLIKE);
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
        return LikeToggleResponse.builder()
                .liked(Objects.equals(like.getStatus(), LikeStatus.LIKED))
                .totalLikeOfPost(totalLikes)
                .build();
    }
}
