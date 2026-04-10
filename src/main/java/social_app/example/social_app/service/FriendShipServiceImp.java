package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.FriendShipResponse;
import social_app.example.social_app.entity.FriendShipType;
import social_app.example.social_app.entity.FriendShips;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.exception.SentFriendShipException;
import social_app.example.social_app.repo.FriendShipRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FriendShipServiceImp implements FriendShipService{
    private final MemberService memberService;
    private final UserService userService;
    private final FriendShipRepository  friendShipRepository;

    @Override
    public FriendShips createFriendShip(FriendShipType status, Integer addresserId, Integer requesterId) {
        Members requester = this.memberService.getMemberById(requesterId);
        Members addresser = this.memberService.getMemberById(addresserId);
        FriendShips friendShip = FriendShips.builder()
                .addresser(addresser)
                .requester(requester)
                .status(status)
                .build();
        this.friendShipRepository.save(friendShip);
        return  friendShip;
    }



    @Override
    public boolean isHasPermission(Integer memberId) {
        Members members = this.memberService.getMemberById(memberId);
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = this.userService.findByUsername(currentUsername);
        return currentUser.getId().equals(members.getUser().getId());
    }

    @Override
    public FriendShips findByAddresserIdAndRequesterId(Integer addresserId, Integer requesterId) {
        return this.friendShipRepository.findByAddresserIdAndRequesterId(addresserId,requesterId)
                .orElseThrow(()-> new NotFoundResource("Not found request"));
    }

    @Override
    public FriendShipResponse sendRequest(Integer requesterId, Integer addresserId) {
        if(!this.isHasPermission(requesterId)){
            throw new SentFriendShipException("You do not have permission");
        }

        if(!this.memberService.isExist(requesterId)||!this.memberService.isExist(addresserId) ){
            throw new SentFriendShipException("Member not exist");
        }

        if(Objects.equals(addresserId, requesterId)){
            throw new SentFriendShipException("Can not send request to yourself");
        }
        //-----create friendship------
        FriendShips friendShip = this.createFriendShip(FriendShipType.PENDING,addresserId,requesterId);
        return FriendShipResponse
                .builder()
                .statusText("pending")
                .message("Sent")
                .build();
    }


@Override
public FriendShipResponse accept(Integer addresserId,Integer requesterId) {
    FriendShips friendShip = this.findByAddresserIdAndRequesterId(addresserId,requesterId);
    if(!this.isHasPermission(addresserId)){
        throw new SentFriendShipException("You do not have permission");
    }
    //---------------Handled-----------
    if(!friendShip.getStatus().equals(FriendShipType.PENDING)) {
        throw new SentFriendShipException("Request handled");
    }
    friendShip.setStatus(FriendShipType.ACCEPTED);
    this.friendShipRepository.save(friendShip);
    return FriendShipResponse
            .builder()
            .message("Accepted Successful")
            .statusText("accepted")
            .build();
}


@Override
public FriendShipResponse denied(Integer addresserId, Integer requesterId) {
    FriendShips friendShip = this.findByAddresserIdAndRequesterId(addresserId,requesterId);
    if(!this.isHasPermission(addresserId)){
        throw new SentFriendShipException("You do not have permission");
    }
    //---------------Handled-----------
    if(!friendShip.getStatus().equals(FriendShipType.PENDING)) {
        throw new SentFriendShipException("Request handled");
    }
    friendShip.setStatus(FriendShipType.DENIED);
    this.friendShipRepository.save(friendShip);
    return FriendShipResponse
            .builder()
            .message("Denied Successful")
            .statusText("denied")
            .build();
}

}

