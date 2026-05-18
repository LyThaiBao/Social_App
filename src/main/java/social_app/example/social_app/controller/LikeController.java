package social_app.example.social_app.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.like.LikeToggleResponse;
import social_app.example.social_app.service.like.LikeService;

import java.security.Principal;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<LikeToggleResponse>> toggleLike(@PathVariable Integer postId, Principal principal){
        LikeToggleResponse response = this.likeService.toggleLike(postId,principal);
        return ResponseEntity.ok().body(ApiResponse.success("Toggle success",response));
    }
}
