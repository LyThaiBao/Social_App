package social_app.example.social_app.dto.usrAndMember;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeResponse {
    private Integer memberId;
    private String username;
    private String fullName;
    private LocalDate birthDay;

}
