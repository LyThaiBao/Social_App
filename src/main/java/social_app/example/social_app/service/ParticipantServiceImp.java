package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.ParticipantRequest;
import social_app.example.social_app.dto.ParticipantResponse;
import social_app.example.social_app.entity.Conversations;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Participants;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.AuthException;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.mapper.ParticipantMapper;
import social_app.example.social_app.repo.ParticipantRepository;

import java.security.Principal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ParticipantServiceImp implements ParticipantService{
    private final ParticipantRepository participantRepository;
    private final MemberService memberService;
    private final UserService userService;
//    private final ConversationService conversationService;
    private final ParticipantMapper participantMapper;
//    @Override
//    public ParticipantResponse createParticipant(Integer memberId,Integer conversationId) {
////        Conversations conversation = this.conversationService.getConversationById(conversationId);
//        Members member = this.memberService.getMemberById(memberId);
//        Participants participant = Participants.builder()
//                .conversation(conversation)
//                .member(member)
//                .build();
//        this.participantRepository.save(participant);
//        return ParticipantResponse.builder()
//                .conversationId(conversation.getId())
//                .memberId(member.getId())
//                .build();
//    }

    @Override
    public ParticipantResponse deleteParticipantById(Integer conversationId, Principal principal) {
        String username = principal.getName();
        Users user = this.userService.findByUsername(username);
        Participants participant = this.participantRepository.findByMemberIdAndConversationId(user.getMember().getId(),conversationId).orElseThrow(()-> new NotFoundResource("Not found participant"));
        this.participantRepository.delete(participant);
        return ParticipantResponse.builder()
                .memberId(participant.getMember().getId())
                .conversationId(participant.getConversation().getId())
                .build();

    }

    @Override
    public List<Participants> getAllParticipant(Principal principal) {
        String username =principal.getName();
        Users user = this.userService.findByUsername(username);
        return this.participantRepository.findAllByMemberId(user.getMember().getId());

    }
}
