package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import social_app.example.social_app.dto.ChatMessage;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Messages;
import social_app.example.social_app.repo.MessageRepository;
import social_app.example.social_app.service.ChatService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

private final SimpMessagingTemplate messagingTemplate;
private final ChatService chatService;
/*
 * Gửi tin nhắn cá nhân (1-1)
 * Client gửi đến: /app/chat.private
*/
    @MessageMapping("/chat.private") // chat 1;1
    public void processPrivateMessage(@Payload ChatMessage chatMessage){
        log.info("Message >>"+chatMessage);
        //FE ko dc gui sender len vi co the gia mao admin,=> dung principal
    // 1. Lưu tin nhắn vào DB thông qua Service
        Messages messageSaved = this.chatService.handlePrivateMessage(chatMessage);
        // 2. Gửi tin nhắn đến người nhận
        // Đường dẫn: /user/{recipientUsername}/queue/private
        //FORM ==> convertAndSendToUser(recipientId,"destination/**",payload)
        this.messagingTemplate.convertAndSendToUser("Phuc Bao","/queue/private",chatMessage);
        // 3. Gửi ngược lại cho chính người gửi để cập nhật UI đồng bộ
//        this.messagingTemplate.convertAndSendToUser(chatMessage.getSenderId(),"/queue/private",chatMessage);
    }

    

}
