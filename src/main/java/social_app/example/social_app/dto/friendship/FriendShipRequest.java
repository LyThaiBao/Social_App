package social_app.example.social_app.dto.friendship;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendShipRequest {
    private Integer requesterId;
    private Integer addresserId;

}
