package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import social_app.example.social_app.dto.ChatMessage;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Messages;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.AuthException;
import social_app.example.social_app.repo.MessageRepository;
import social_app.example.social_app.service.ChatService;
import social_app.example.social_app.service.MemberService;
import social_app.example.social_app.service.ParticipantService;
import social_app.example.social_app.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

private final SimpMessagingTemplate messagingTemplate;
private final ParticipantService participantService;
private final ChatService chatService;
private final UserService userService;
/*
 * Gửi tin nhắn cá nhân (1-1)
 * Client gửi đến: /app/chat.private
*/
        @MessageMapping("/chat.private") // chat 1;1
    public void processPrivateMessage(@Payload ChatMessage chatMessage,Principal principal){ // principal lay thong tin user dang login
        //------------GET Principal-------------
        if(principal == null){
            throw new AuthException("Unauthenticated");
        }
        String senderName = principal.getName();
        Users sender = this.userService.findByUsername(senderName);
        chatMessage.setSenderId(sender.getId()); // set bang senderID lay tu principal

        //BE ko tin sender FE gui len vi co the gia mao admin,=> dung principal
        // Bcs login by User so Principal save user info
        String destinationUser = this.chatService.getUsernameDest(chatMessage);

        // 1. Lưu tin nhắn vào DB thông qua Service
        Messages messageSaved = this.chatService.saveMessage(chatMessage);
            System.out.println(">>> Message saved: "+messageSaved);
        // 2. Gửi tin nhắn đến người nhận
        // Đường dẫn: /user/{recipientUsername}/queue/private
        this.messagingTemplate.convertAndSendToUser(destinationUser,"/queue/private",messageSaved);
        // 3. Gửi ngược lại cho chính người gửi để cập nhật UI đồng bộ
        this.messagingTemplate.convertAndSendToUser(senderName,"/queue/private",messageSaved);
    }

    @MessageMapping("/public.group.{groupId}")
    public void processPublicMessage(@Payload ChatMessage chatMessage, @DestinationVariable Integer groupId, Principal principal){
        //------------GET Principal-------------
        if(principal == null){
            throw new AuthException("Unauthenticated");
        }
        String senderName = principal.getName();
        Users sender = this.userService.findByUsername(senderName);
        chatMessage.setSenderId(sender.getId()); // set bang senderID lay tu principal

        // 1. Lưu tin nhắn vào DB thông qua Service
        Messages messageSaved = this.chatService.saveMessage(chatMessage);
        String destination = "/topic/group." + groupId;//FE sub ==> /topic/group.groupId
        this.messagingTemplate.convertAndSend(destination,messageSaved);
    }

    @MessageMapping("/public.type.{groupId}")
    public void processTypingMessage(@DestinationVariable Integer groupId, Principal principal){
        //------------GET Principal-------------
        if(principal == null){
            throw new AuthException("Unauthenticated");
        }
        String senderName = principal.getName();
        String destination = "/topic/type." + groupId;//FE sub ==> /topic/group.groupId
        this.messagingTemplate.convertAndSend(destination,senderName);
    }


    @MessageMapping("/public.leave.{groupId}")
    public void processLeaveGroup(@DestinationVariable Integer groupId, Principal principal){
        //------------GET Principal-------------
        if(principal == null){
            throw new AuthException("Unauthenticated");
        }
        String senderName = principal.getName();
        this.participantService.deleteParticipantById(groupId,principal);
        String destination = "/topic/leave." + groupId;//FE sub ==> /topic/group.groupId
        this.messagingTemplate.convertAndSend(destination,senderName);
    }







}
