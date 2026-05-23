package social_app.example.social_app.service.post;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.post.PostRequest;
import social_app.example.social_app.dto.post.PostResponse;
import social_app.example.social_app.entity.Likes;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Posts;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.ForbiddenException;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.mapper.PostMapper;
import social_app.example.social_app.repo.LikeRepository;
import social_app.example.social_app.repo.PostRepository;
import social_app.example.social_app.service.like.LikeService;
import social_app.example.social_app.service.member.MemberService;
import social_app.example.social_app.service.usr.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService{
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final PostMapper postMapper;
    private final UserService userService;

    // dirty inject
    private final LikeRepository likeRepository;
    @Override
    public PostResponse createPost(PostRequest request,Principal principal) {

        Users user = this.userService.findByUsername(principal.getName());
        Members member = this.memberService.getMemberById(user.getMember().getId());
        Posts post = Posts.builder()
                .member(member)
                .content(request.getContent())
                .status(request.getStatus())
                .mediaUrl(request.getMediaUrl())
                .mediaType(request.getMediaType())
                .build();
        log.info(">>>POST: "+post);
        this.postRepository.save(post);
        return this.postMapper.convertToPostResponse(post);
    }

    @Override
    public Page<PostResponse> getNewPosts(Principal principal,int pageNum,int size) {
        Users user = this.userService.findByUsername(principal.getName());
        Pageable pageable = (Pageable) PageRequest.of(pageNum,size);
        Page<Posts> postsPage = this.postRepository.findNewsfeedPosts(user.getMember().getId(),pageable);
        List<Integer> listId = postsPage.getContent().stream().map(Posts::getId).toList();
        Set<Integer> likedPostIds = this.likeRepository.findLikedPostIds(user.getMember().getId(),listId);
        
        return postsPage.map(post->{
            PostResponse response = postMapper.convertToPostResponse(post);
           response.setLiked(likedPostIds.contains(post.getId()));
            return response;
        });
    }

    @Override
    @Transactional
    public void deletePost(Integer id,Principal principal) {
        String currentUser = principal.getName();
        Users user = this.userService.findByUsername(currentUser);
        int rowDelete = this.postRepository.softDelete(id,user.getMember().getId());
        if(rowDelete == 0){
            throw new NotFoundResource("Failure delete post "+id);
        }
    }

    @Override
    @Transactional
    public PostResponse modifyPost(Integer id,PostRequest request) {
        Posts post = this.postRepository.findById(id).orElseThrow(() -> new NotFoundResource("Not found post with id "+id));
        if(request.getContent()!=null){
            post.setContent(request.getContent());
        }
        if(request.getStatus()!=null){
            post.setStatus(request.getStatus());
        }
        if(request.getMediaUrl()!=null){
            post.setMediaUrl(request.getMediaUrl());
        }
        return this.postMapper.convertToPostResponse(post);
    }

    @Override
    public Posts getPostEntity(Integer id) {
        return this.postRepository.findById(id).orElseThrow(()-> new NotFoundResource("Not found post with id "+id));
    }

    @Override
    public void incrementLike(Integer postId) {
        this.postRepository.incrementLike(postId);
    }

    @Override
    public void reduceLike(Integer postId) {
        this.postRepository.reduceLike(postId);
    }

    @Override
    public Long getTotalLikes(Integer postId) {
        return this.postRepository.getTotalLikes(postId);
    }
}
