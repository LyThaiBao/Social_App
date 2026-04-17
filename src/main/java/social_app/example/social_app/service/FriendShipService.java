package social_app.example.social_app.service;

import social_app.example.social_app.dto.FriendShipDetail;
import social_app.example.social_app.dto.FriendShipResponse;
import social_app.example.social_app.entity.FriendShipType;
import social_app.example.social_app.entity.FriendShips;

public interface FriendShipService {
     FriendShips createFriendShip(FriendShipType status,Integer addresser, Integer requester);
     boolean isHasPermission(Integer userId);
     FriendShips findByAddresserIdAndRequesterId(Integer requesterId, Integer AddresserId);
     FriendShipDetail findBothId(Integer AddresserId,Integer requesterId);
     FriendShipResponse sendRequest(Integer requesterId,Integer addresserId);
     FriendShipResponse accept(Integer addresserId,Integer requesterId);
     FriendShipResponse denied(Integer  addresserId,Integer requesterId);

}
