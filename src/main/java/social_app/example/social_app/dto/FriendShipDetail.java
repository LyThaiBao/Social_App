package social_app.example.social_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import social_app.example.social_app.entity.FriendShipType;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendShipDetail {

    private Integer id;
    private Integer requesterId;
    private Integer addresserId;
    private FriendShipType friendShipType;

}
