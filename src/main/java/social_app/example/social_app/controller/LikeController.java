package social_app.example.social_app.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.like.LikeResponse;
import social_app.example.social_app.service.like.LikeService;

import java.security.Principal;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    @GetMapping("/toggleLike/{postId}")
    public ResponseEntity<ApiResponse<LikeResponse>> toggleLike(@PathVariable Integer postId, Principal principal){
        LikeResponse response = this.likeService.toggleLike(postId,principal);
        return ResponseEntity.ok().body(ApiResponse.success("Toggle success",response));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse<LikeResponse>> getLikeOfPost(@PathVariable Integer postId, Principal principal){
        LikeResponse response = this.likeService.getLikesOfPost(postId,principal);
        return ResponseEntity.ok().body(ApiResponse.success("Get Success",response));
    }
}
