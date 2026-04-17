package social_app.example.social_app.mapper;

import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.FriendShipDetail;
import social_app.example.social_app.entity.FriendShips;

@Component
public class FriendShipMapper {

    public FriendShipDetail convertToFriendShipDetail(FriendShips friendShip){
        return FriendShipDetail.builder()
                .id(friendShip.getId())
                .addresserId(friendShip.getAddresser().getId())
                .requesterId(friendShip.getRequester().getId())
                .friendShipType(friendShip.getStatus())
                .build();
    }
}
