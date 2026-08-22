package social_app.example.social_app.service.conv;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.conv.ConversationResponse;
import social_app.example.social_app.service.participant.ParticipantService;
import social_app.example.social_app.service.usr.UserService;
import social_app.example.social_app.type.ConversationType;
import social_app.example.social_app.entity.Conversations;
import social_app.example.social_app.entity.Participants;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.mapper.ConversationMapper;
import social_app.example.social_app.repo.ConversationRepository;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ConversationServiceImp implements ConversationService {
    private  final ConversationRepository conversationRepository;
    private  final ParticipantService participantService;
    private final UserService userService;
    private final ConversationMapper conversationMapper;

    @Override
    public Conversations createPrivateConversation(Integer currentMemberId,Integer partnerId) {
        Conversations conversation =  Conversations.builder()
                .conversationName(currentMemberId+"_"+partnerId) // placeholder
                .type(ConversationType.PRIVATE)
                .build();

       Conversations conversationSaved =  this.conversationRepository.save(conversation);
        this.participantService.createParticipant(currentMemberId,conversationSaved); // saved
        this.participantService.createParticipant(partnerId,conversationSaved);// saved
        return  conversationSaved;
    }

    @Override
    public Conversations getConversationEntityById(Integer id) {
       return this.conversationRepository.findById(id).orElseThrow(()-> new NotFoundResource("Not found conversation"));

    }
    @Override
    public String resolveConversationName(Conversations conversation, Integer currentMemberId) {
        StringBuilder conversationName;
        if(conversation.getType() == ConversationType.PRIVATE){
            conversationName = new StringBuilder(this.participantService.getFullName(conversation.getId(),currentMemberId));
        }
        else{
            conversationName = new StringBuilder(conversation.getConversationName());
        }
        return conversationName.toString();
    }

    @Override
    public ConversationResponse findOrCreatePrivateConversation(Principal principal , Integer partnerId) {
    String username  = principal.getName();
    Users user = this.userService.findByUsername(username);
     Conversations conversationEx = this.participantService.isExitsPrivateConv(user.getMember().getId(),partnerId);
     if(conversationEx != null){
         return this.conversationMapper.convertToConversationResponse(conversationEx);
     }
     Conversations conversation = this.createPrivateConversation(user.getMember().getId(),partnerId);
     return this.conversationMapper.convertToConversationResponse(conversation);

    }

    @Override
    public List<ConversationResponse> getConversations(Principal principal) {
        String username = principal.getName();
        Users user = this.userService.findByUsername(username);

        List<Conversations> conversations =  this.participantService.getConversations(user.getMember().getId());
        return conversations.stream().map(c ->{
            String cnvName = this.resolveConversationName(c,user.getMember().getId());
            c.setConversationName(cnvName);
            return this.conversationMapper.convertToConversationResponse(c);
        }).toList();
    }

    @Override
    public ConversationResponse getConversation(Integer id,Principal principal) {
        Conversations conversation = this.conversationRepository.findById(id).orElseThrow(() -> new NotFoundResource("Not found conversation"));
        String username = principal.getName();
        Users user = this.userService.findByUsername(username);
        return ConversationResponse.builder()
                .type(conversation.getType())
                .conversationId(conversation.getId())
                .conversationName(this.resolveConversationName(conversation,user.getMember().getId()))
                .build();
    }


}
