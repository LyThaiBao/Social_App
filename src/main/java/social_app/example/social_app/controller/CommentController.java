package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.comment.CommentRequest;
import social_app.example.social_app.dto.comment.CommentResponse;
import social_app.example.social_app.dto.comment.DeleteRequest;
import social_app.example.social_app.service.comment.CommentService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(@RequestBody CommentRequest request, Principal principal){
        CommentResponse response = this.commentService.createComment(request,principal);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Create success",response));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<String>> softDeleteComment(@PathVariable Integer postId, @PathVariable Integer commentId,Principal principal){
        String result = this.commentService.deleteComment(postId,commentId,principal);
        return ResponseEntity.ok().body(ApiResponse.success("Deleted",result));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getAllComments(@PathVariable Integer postId){
        List<CommentResponse> response = this.commentService.getAllComments(postId);
        return ResponseEntity.ok().body(ApiResponse.success("Get all success",response));
    }
}
