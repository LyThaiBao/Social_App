package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.ConversationResponse;
import social_app.example.social_app.service.ConversationService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;
    @GetMapping
    public ResponseEntity<ApiResponse<List<ConversationResponse>>> getConversations(Principal principal){
        List<ConversationResponse> conversationResponseList = this.conversationService.getConversations(principal);
        return ResponseEntity.ok().body(ApiResponse.success("Get Conversations success",conversationResponseList));
    }
}
