package social_app.example.social_app.controller;

import com.cloudinary.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.post.GetPostRequest;
import social_app.example.social_app.dto.post.PostRequest;
import social_app.example.social_app.dto.post.PostResponse;
import social_app.example.social_app.service.post.PostService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestBody PostRequest request,Principal principal){
        PostResponse response = this.postService.createPost(request,principal);
        return ResponseEntity.ok().body(ApiResponse.success("Create success",response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getNewPosts(Principal principal,
    @RequestParam(value = "pageNum",defaultValue = "0") int pageNum,
    @RequestParam(value = "size",defaultValue = "15") int size
    ){
        Page<PostResponse> responses = this.postService.getNewPosts(principal,pageNum,size);
        return ResponseEntity.ok(ApiResponse.success("Get All Success",responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> softDeletePost(@PathVariable Integer id,Principal principal){
        this.postService.deletePost(id,principal);
        return ResponseEntity.ok().body(ApiResponse.success("Delete Success",""));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> modifyPost(@PathVariable Integer id,@RequestBody PostRequest request){
        PostResponse response = this.postService.modifyPost(id,request);
        return ResponseEntity.ok().body(ApiResponse.success("Path success",response));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Integer postId,Principal principal){
        log.info(">>>ID: "+postId);
        PostResponse response = this.postService.getPost(postId,principal);
        log.info("<<< POST: "+response);
        return ResponseEntity.ok().body(ApiResponse.success("Get success",response));
    }

    @GetMapping("/personal")
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getMyPosts(Principal principal,
    @RequestParam(value = "page",defaultValue = "0") int page, @RequestParam(value = "size",defaultValue = "15") int size,@RequestParam(value = "memberId") Integer memberId){
        log.info(">>>GET MY POSTS");
        Page<PostResponse> responses = this.postService.getPersonalPosts(principal,memberId,page,size);
        log.info(">>>PAGE: "+responses);
        return ResponseEntity.ok(ApiResponse.success("Get Success",responses));
    }

}
