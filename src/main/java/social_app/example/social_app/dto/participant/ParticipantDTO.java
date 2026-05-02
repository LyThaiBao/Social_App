package social_app.example.social_app.dto.participant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.dto.usrAndMember.MemberResponse;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParticipantDTO {
    private Integer conversationId;
    private List<MemberResponse> responseList;
}
