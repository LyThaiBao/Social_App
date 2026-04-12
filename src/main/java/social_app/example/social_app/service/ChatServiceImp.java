package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.ChatMessage;
import social_app.example.social_app.entity.Conversations;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Messages;
import social_app.example.social_app.entity.Users;

@Service
@RequiredArgsConstructor
public class ChatServiceImp implements ChatService{
    private final MemberService memberService;
    private final ConversationService conversationService;
    private final MessageService messageService;
    private final UserService userService;
    @Override
    public Messages saveMessage(ChatMessage chatMessage) {
        System.out.println("Sender >>> "+chatMessage.getSenderId());
        Members sender = this.memberService.getMemberById(chatMessage.getSenderId());
        Conversations conversation = this.conversationService.getConversationById(chatMessage.getConversationId());
        Messages message = Messages
                .builder()
                .type(chatMessage.getMessageType())
                .content(chatMessage.getContent())
                .sender(sender)
                .conversation(conversation)
                .build();
        System.out.println("Message >>>"+message);
        return this.messageService.save(message);

    }

    @Override
    public String getUsernameDest(ChatMessage chatMessage) {
        Members member = this.memberService.getMemberById(chatMessage.getRecipientId());
        Users user = this.userService.findByUserId(member.getUser().getId());
        return user.getUsername();

    }
}
