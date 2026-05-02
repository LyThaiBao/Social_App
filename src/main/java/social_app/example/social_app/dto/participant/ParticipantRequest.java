package social_app.example.social_app.dto.participant;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParticipantRequest {
    private Integer conversationId;
    private Integer memberId;
    private Principal principal;
}
