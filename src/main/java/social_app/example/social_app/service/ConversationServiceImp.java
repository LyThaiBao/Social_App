package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.ConversationResponse;
import social_app.example.social_app.entity.ConversationType;
import social_app.example.social_app.entity.Conversations;
import social_app.example.social_app.entity.Participants;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.repo.ConversationRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConversationServiceImp implements ConversationService{
    private  final ConversationRepository conversationRepository;
    private  final ParticipantService participantService;
    private final UserService userService;
    @Override
    public Conversations getConversationById(Integer id) {
       return this.conversationRepository.findById(id).orElseThrow(()-> new NotFoundResource("Not found conversation"));
    }

    @Override
    public List<ConversationResponse> getConversations(Principal principal) {
        String username = principal.getName();
        Users user = this.userService.findByUsername(username);
        List<Participants> participantsList = this.participantService.getAllParticipant(principal);
       List<ConversationResponse> conversationResponses =  participantsList.stream().map(s->{
           String conversationName = "";
           Conversations conversation = s.getConversation();
           if(conversation.getType() == ConversationType.PRIVATE){
           Participants partner =   conversation.getParticipantsList().stream()
                   .filter(p->!p.getMember().getId().equals(user.getMember().getId()))
                   .findFirst().orElse(null);
                conversationName = partner!=null ? partner.getMember().getFullName() :"Unknow";
           }
           else{
               conversationName = conversation.getConversationName();
           }

            return ConversationResponse.builder()
                    .conversationId(s.getConversation().getId())
                    .type(conversation.getType())
                    .conversationName(conversationName)
                    .build();
        }).toList();
        return  conversationResponses;
    }
}
