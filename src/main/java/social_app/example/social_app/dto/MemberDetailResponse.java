package social_app.example.social_app.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailResponse {
    private Integer id;

    private String fullName;

    private LocalDate birthDay;

    private Instant joinDay;
}
