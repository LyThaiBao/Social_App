package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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


}
