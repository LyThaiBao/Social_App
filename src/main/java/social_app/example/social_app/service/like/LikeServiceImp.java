package social_app.example.social_app.service.like;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.like.LikeResponse;
import social_app.example.social_app.entity.Likes;
import social_app.example.social_app.entity.Posts;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.mapper.LikeMapper;
import social_app.example.social_app.repo.LikeRepository;
import social_app.example.social_app.service.post.PostService;
import social_app.example.social_app.service.usr.UserService;
import social_app.example.social_app.type.LikeStatus;

import java.security.Principal;
import java.util.List;
import java.util.Set;


@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImp implements LikeService{
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;
    private final LikeMapper likeMapper;
    @Override
    @Transactional
    public LikeResponse toggleLike(Integer postId, Principal principal) {
        String currentUsername =  principal.getName();
        Users user = this.userService.findByUsername(currentUsername);
        Posts post = this.postService.getPostEntity(postId);
        Likes like = this.likeRepository.getLikeByMemberIdAndPostId(user.getMember().getId(),postId);
        if(like!=null){
            boolean isLiked = like.getStatus() == LikeStatus.LIKED;
            like.setStatus(isLiked?LikeStatus.UNLIKE:LikeStatus.LIKED);
            if (isLiked) {
                this.postService.reduceLike(postId);
            }
            else{
                this.postService.incrementLike(postId);
            }
        }
        else{

            like = Likes.builder()
                    .member(user.getMember())
                    .status(LikeStatus.LIKED)
                    .post(post)
                    .build();
            this.postService.incrementLike(postId);
        }
        this.likeRepository.save(like);


        Long totalLikes = this.postService.getTotalLikes(postId);
        return LikeResponse.builder()
                .liked(like.getStatus() == LikeStatus.LIKED)
                .postId(postId)
                .totalLikeOfPost(totalLikes)
                .build();
    }



}
