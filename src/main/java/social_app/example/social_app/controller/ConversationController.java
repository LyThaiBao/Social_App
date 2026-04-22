package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.ConversationRequest;
import social_app.example.social_app.dto.ConversationResponse;
import social_app.example.social_app.service.ConversationService;

import java.security.Principal;
import java.util.List;

@Slf4j
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConversationResponse>> getConversation(@PathVariable Integer id,Principal principal){
        ConversationResponse conversationResponse = this.conversationService.getConversation(id,principal);
        return ResponseEntity.ok().body(ApiResponse.success("Get conversation Success",conversationResponse));
    }

    @PostMapping
    public  ResponseEntity<ApiResponse<ConversationResponse>> findOrCreateConversation(@RequestBody ConversationRequest request,Principal principal){
        ConversationResponse conversationResponse = this.conversationService.findOrCreatePrivateConversation(principal, request.getPartnerId());
        return ResponseEntity.ok().body(ApiResponse.success("Post Success",conversationResponse));
    }
}
