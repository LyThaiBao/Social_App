package social_app.example.social_app.service;

import social_app.example.social_app.dto.FriendShipResponse;

public interface FriendShipService {
     boolean isHasPermission(Integer userId);
     FriendShipResponse sendRequest(Integer requesterId,Integer addresserId);
     FriendShipResponse accept(Integer addresserId,Integer requesterId);
     FriendShipResponse denied(Integer  addresserId,Integer requesterId);

}
