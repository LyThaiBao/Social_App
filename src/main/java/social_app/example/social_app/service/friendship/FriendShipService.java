package social_app.example.social_app.service.friendship;

import social_app.example.social_app.dto.friendship.FriendShipDetail;
import social_app.example.social_app.dto.friendship.FriendShipResponse;
import social_app.example.social_app.type.FriendShipType;
import social_app.example.social_app.entity.FriendShips;

import java.security.Principal;

public interface FriendShipService {
     FriendShips createFriendShip(FriendShipType status,Integer addresser, Integer requester);
     boolean isHasPermission(Integer userId);
     FriendShips findByRequesterIdAndAddresserId(Integer requesterId, Integer AddresserId);
     FriendShipDetail findBothId(Integer AddresserId,Integer requesterId);
     FriendShipResponse sendRequest(Integer requesterId,Integer addresserId,Principal principal);
     FriendShipResponse accept(Integer addresserId,Integer requesterId);
     FriendShipResponse denied(Integer  addresserId,Integer requesterId);
     FriendShipResponse unFriend(Integer requesterId,Integer addresserId);
     FriendShipResponse cancelRequest(Integer requesterId, Integer addresserId);

}
