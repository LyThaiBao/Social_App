package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.ChatMessage;
import social_app.example.social_app.entity.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImp implements ChatService{
    private final MemberService memberService;
    private final ConversationService conversationService;
    private final MessageService messageService;
    private final UserService userService;
    private final  ParticipantService participantService;
    @Override
    public Messages saveMessage(ChatMessage chatMessage) {
        Members sender = this.memberService.getMemberById(chatMessage.getSenderId());
        Conversations conversation = this.conversationService.getConversationEntityById(chatMessage.getConversationId());
        Messages message = Messages
                .builder()
                .type(chatMessage.getMessageType())
                .content(chatMessage.getContent())
                .sender(sender)
                .conversation(conversation)
                .build();
        return this.messageService.save(message);

    }

    @Override
    public String getUsernameDest(ChatMessage chatMessage) {
        // TAM THOI COMMENT DE RUN WS DE HON
//        String username = principal.getName();
        Members owner = this.memberService.getMemberById(chatMessage.getSenderId());
        log.info("Sender ID "+chatMessage.getSenderId());
//        Users users = this.userService.findByUsername(username);
        List<Participants> participants = this.participantService.getByConversationId(chatMessage.getConversationId());
        List<Participants> participantsOfPartner =  participants.stream().filter(p -> !Objects.equals(p.getMember().getId(),owner.getId())).toList();
        if(!participantsOfPartner.isEmpty()){
            Members member = participants.getFirst().getMember();
            Users user = this.userService.findByUserId(member.getUser().getId());
            return user.getUsername();
        }

        return  null;
    }
}
