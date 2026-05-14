package social_app.example.social_app.service.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.post.PostRequest;
import social_app.example.social_app.dto.post.PostResponse;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Posts;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.mapper.PostMapper;
import social_app.example.social_app.repo.PostRepository;
import social_app.example.social_app.service.member.MemberService;
import social_app.example.social_app.service.usr.UserService;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService{
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final PostMapper postMapper;
    private final UserService userService;
    @Override
    public PostResponse createPost(PostRequest request) {
        Members member = this.memberService.getMemberById(request.getMemberId());

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
    public List<PostResponse> getAllPosts(Principal principal) {
        Users user = this.userService.findByUsername(principal.getName());
        List<Posts> list = this.postRepository.findAllByMemberId(user.getMember().getId());
        return list.stream().map(this.postMapper::convertToPostResponse).toList();
    }
}
