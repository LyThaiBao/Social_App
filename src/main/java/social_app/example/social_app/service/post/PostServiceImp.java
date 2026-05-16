package social_app.example.social_app.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.post.PostRequest;
import social_app.example.social_app.dto.post.PostResponse;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Posts;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.ForbiddenException;
import social_app.example.social_app.mapper.PostMapper;
import social_app.example.social_app.repo.PostRepository;
import social_app.example.social_app.service.member.MemberService;
import social_app.example.social_app.service.usr.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService{
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final PostMapper postMapper;
    private final UserService userService;
    @Override
    public PostResponse createPost(PostRequest request,Principal principal) {
        Members member = this.memberService.getMemberById(request.getMemberId());
        Users user = this.userService.findByUsername(principal.getName());
        Integer myId = this.memberService.getMemberById(user.getMember().getId()).getId();
        if(!Objects.equals(myId,request.getMemberId())){
            throw new ForbiddenException("You do not have permission");
        }
        Posts post = Posts.builder()
                .member(member)
                .content(request.getContent())
                .status(request.getStatus())
                .mediaUrl(request.getMediaUrl())
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
        return postsPage.map(this.postMapper::convertToPostResponse);
    }
}
