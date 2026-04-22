package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.MessageRequest;
import social_app.example.social_app.dto.MessageResponse;
import social_app.example.social_app.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    @PostMapping
    public ResponseEntity<ApiResponse<List<MessageResponse>>> getMessagesByConversationId(@RequestBody MessageRequest request){
        List<MessageResponse> messageResponses =  this.messageService.getMessageByConversationId(request.getConversationId());
        return ResponseEntity.ok().body(ApiResponse.success("Get message success",messageResponses));
    }
}
