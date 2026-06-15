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
                .conversationName(currentMemberId+"_"+partnerId) // tuong trung --> do getConversation chi lay ten cua partner
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
        String conversationName = "";
        if(conversation.getType() == ConversationType.PRIVATE){
            Participants partner =   conversation.getParticipantsList().stream()
                    .filter(p->!p.getMember().getId().equals(currentMemberId))
                    .findFirst().orElse(null);
            conversationName = partner!=null ? partner.getMember().getFullName() :"Unknow";
        }
        else{
            conversationName = conversation.getConversationName();
        }
        return conversationName;
    }

    @Override
    public ConversationResponse findOrCreatePrivateConversation(Principal principal , Integer partnerId) {
    String username  = principal.getName();
    Users user = this.userService.findByUsername(username);
     List<Participants> participantsList = this.participantService.getAllParticipant(principal);
     for (Participants p: participantsList){
            Conversations cvn = p.getConversation();
            if(cvn.getType() == ConversationType.PRIVATE){
              boolean isHasPartner =  cvn.getParticipantsList().stream().anyMatch(pr -> Objects.equals(pr.getMember().getId(), partnerId));
             if(isHasPartner){
                 return this.conversationMapper.convertToConversationResponse(cvn);
             }

            }
     }
     Conversations conversation = this.createPrivateConversation(user.getMember().getId(),partnerId);
     return this.conversationMapper.convertToConversationResponse(conversation);

    }

    @Override
    public List<ConversationResponse> getConversations(Principal principal) {
        String username = principal.getName();
        Users user = this.userService.findByUsername(username);
        List<Participants> participantsList = this.participantService.getAllParticipant(principal);
        return participantsList.stream().map(s->{
            return ConversationResponse.builder()
                    .conversationId(s.getConversation().getId())
                    .type(s.getConversation().getType())
                    .conversationName(this.resolveConversationName(s.getConversation(),user.getMember().getId()))
                    .build();
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
