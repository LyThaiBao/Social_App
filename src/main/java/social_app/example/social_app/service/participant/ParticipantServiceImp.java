package social_app.example.social_app.service.participant;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.participant.ParticipantResponse;
import social_app.example.social_app.entity.Conversations;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Participants;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.repo.ParticipantRepository;
import social_app.example.social_app.service.usr.UserService;
import social_app.example.social_app.service.member.MemberService;

import java.security.Principal;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ParticipantServiceImp implements ParticipantService {
    private final ParticipantRepository participantRepository;
    private final MemberService memberService;
    private final UserService userService;

    @Override
    public ParticipantResponse createParticipant(Integer memberId,Conversations conversation) {
        Members member = this.memberService.getMemberById(memberId);
        Participants participant = Participants.builder()
                .conversation(conversation)
                .member(member)
                .build();
        this.participantRepository.save(participant);
        return ParticipantResponse.builder()
                .conversationId(conversation.getId())
                .memberId(member.getId())
                .build();
    }

    @Override
    @Transactional
    public void deleteParticipantById(Integer conversationId, Principal principal) {
        String username = principal.getName();
        Users user = this.userService.findByUsername(username);
        int rowDeleted = this.participantRepository.deleteByMemberIdAndConversationId(user.getMember().getId(),conversationId);
        if(rowDeleted == 0){
            throw new NotFoundResource("Not found participant");
        }
    }



    @Override
    public String getDesUsername(Integer cvnId, Integer senderId) {
        return this.participantRepository.getUsernameFromParticipant(cvnId,senderId);
    }

    @Override
    public String getFullName(Integer cvnId, Integer senderId) {
        return this.participantRepository.getFullNameFromParticipant(cvnId,senderId);
    }

    @Override
    public Conversations isExitsPrivateConv(Integer ownerId, Integer partnerId) {
        return this.participantRepository.isExitsPrivateConv(ownerId,partnerId);
    }

    @Override
    public List<Conversations> getConversations(Integer ownerId) {
        return  this.participantRepository.getConversations(ownerId);
    }
}
