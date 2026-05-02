package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import social_app.example.social_app.dto.msg.ChatMessage;
import social_app.example.social_app.dto.msg.MessageResponse;
import social_app.example.social_app.dto.msg.RecallMessageRequest;
import social_app.example.social_app.dto.notification.NewMessage;
import social_app.example.social_app.dto.notification.NotificationResponse;
import social_app.example.social_app.dto.typing.TypingRequest;
import social_app.example.social_app.dto.typing.TypingResponse;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Messages;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.AuthException;
import social_app.example.social_app.service.chat.ChatService;
import social_app.example.social_app.service.member.MemberService;
import social_app.example.social_app.service.msg.MessageService;
import social_app.example.social_app.service.notifi.NotificationService;
import social_app.example.social_app.service.participant.ParticipantService;
import social_app.example.social_app.service.usr.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

private final SimpMessagingTemplate messagingTemplate;
private final ParticipantService participantService;
private final ChatService chatService;
private final MessageService messageService;
private final UserService userService;
private final MemberService memberService;
//private final NotificationService notificationService;
/*
 * Gửi tin nhắn cá nhân (1-1)
 * Client gửi đến: /app/chat.private
*/
        @MessageMapping("/chat.private") // chat 1;1
    public void processPrivateMessage(@Payload ChatMessage chatMessage){
            log.info(" >>> LOG LOG"+chatMessage);

         //Code chat chatType if(chatType == chat ) ==> send signal for Fe show icon typing ....

            
        //------------GET Principal-------------
//        if(principal == null){
//            throw new AuthException("Unauthenticated");
//        }
//        String senderNam = principal.getName();
//        log.info(">>>NAME: "+senderNam);
//        Users sender = this.userService.findByUsername(senderName);
//        chatMessage.setSenderId(sender.getId()); // set bang senderID lay tu principal

        //BE ko tin sender FE gui len vi co the gia mao admin,=> dung principal
        // Bcs login by User so Principal save user info
        String senderName =  this.userService.findByUserId(chatMessage.getSenderId()).getUsername(); // tam thoi tin
        String destinationUser = this.chatService.getUsernameDest(chatMessage);

        // 1. Lưu tin nhắn vào DB thông qua Service
            Messages messageSaved = this.chatService.saveMessage(chatMessage);
            MessageResponse messageResponse = this.messageService.getMessageResponse(messageSaved);
            // thong bai tin nhan moi
//            NotificationResponse notificationResponse = this.notificationService.newMessageResponse(messageResponse);
        // 2. Gửi tin nhắn đến người nhận
        // Đường dẫn: /user/{recipientUsername}/queue/private
//        this.messagingTemplate.convertAndSendToUser(destinationUser,"/queue/private",messageResponse);
            this.messagingTemplate.convertAndSend("/queue/private-" +messageResponse.getConversationId(),messageResponse);
            this.messagingTemplate.convertAndSend("/queue/notification", messageResponse);
        // 3. Gửi ngược lại cho chính người gửi để cập nhật UI đồng bộ
//        this.messagingTemplate.convertAndSendToUser(senderName,"/queue/private",messageResponse);
    }

    @MessageMapping("/chat.recall")
    public void recallMessage(@Payload RecallMessageRequest recallMessageRequest){
            log.info("ON RECALLED"+recallMessageRequest);
        MessageResponse messageResponse = this.messageService.recall(recallMessageRequest.getId());
        log.info(">>> MSG: "+messageResponse);
        this.messagingTemplate.convertAndSend("/queue/recall-"+messageResponse.getConversationId(),messageResponse);


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

    @MessageMapping("/public.type.{groupId}") // typing...
    public void processTypingMessage(@DestinationVariable Integer groupId, @Payload TypingRequest request){
            log.info("TYPING..................................."+request);
        //------------GET Principal-------------
//        if(principal == null){ gia lap tam thoi
//            throw new AuthException("Unauthenticated");
//        }
//        String senderName = principal.getName();
        Members member = this.memberService.getMemberById(request.getSenderId());
        String senderName = member.getUser().getUsername();

        TypingResponse response = TypingResponse.builder()
                .memberName(member.getFullName())
                .senderId(request.getSenderId())
                .build();
        String destination = "/topic/type." + groupId;//FE sub ==> /topic/group.groupId
        this.messagingTemplate.convertAndSend(destination,response);
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
