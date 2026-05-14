package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestBody PostRequest request){
        PostResponse response = this.postService.createPost(request);
        return ResponseEntity.ok().body(ApiResponse.success("Create success",response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts(Principal principal){
        List<PostResponse> responses = this.postService.getAllPosts(principal);
        return ResponseEntity.ok(ApiResponse.success("Get All Success",responses));
    }


}
