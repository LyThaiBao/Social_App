package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.ChatMessage;
import social_app.example.social_app.entity.Conversations;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Messages;

@Service
@RequiredArgsConstructor
public class ChatServiceImp implements ChatService{
    private final MemberService memberService;
    private final ConversationService conversationService;
    private final MessageService messageService;
    @Override
    public Messages handlePrivateMessage(ChatMessage chatMessage) {
        System.out.println("Sender >>> "+chatMessage.getSenderId());
        Members sender = this.memberService.getMemberByFullName(chatMessage.getSenderId()).orElseThrow(()->new RuntimeException("Not found sender"));
        Conversations conversation = this.conversationService.getConversationById(chatMessage.getConversationId()).orElseThrow(()->new RuntimeException("Not found conversation"));
        Messages message = Messages
                .builder()
                .type(chatMessage.getMessageType())
                .content(chatMessage.getContent())
                .sender(sender)
                .conversation(conversation)
                .build();
        System.out.println("Message >>>"+message);
        return  this.messageService.save(message);

    }
}
