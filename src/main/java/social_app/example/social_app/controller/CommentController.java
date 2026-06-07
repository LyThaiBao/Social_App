package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.comment.CommentRequest;
import social_app.example.social_app.dto.comment.CommentResponse;
import social_app.example.social_app.dto.comment.DeleteRequest;
import social_app.example.social_app.service.comment.CommentService;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/comments.{postId}")
    public void createComment(@DestinationVariable Integer postId, @Payload CommentRequest request, Principal principal){
        log.info(">>>Log: comment "+request);
        CommentResponse response = this.commentService.createComment(request,principal);
       this.simpMessagingTemplate.convertAndSend("/topic/posts."+postId+"/comments",response);
    }

//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<ApiResponse<String>> softDeleteComment(@PathVariable Integer postId, @PathVariable Integer commentId,Principal principal){
//        String result = this.commentService.deleteComment(postId,commentId,principal);
//        return ResponseEntity.ok().body(ApiResponse.success("Deleted",result));
//   g }
//
    @SubscribeMapping("/comments.{postId}")
    public List<CommentResponse> getAllComments(@DestinationVariable Integer postId){
        log.info(">>>SUBSRIBE: ",+postId);
        List<CommentResponse> response = this.commentService.getAllComments(postId);
        return response;
    }
}
