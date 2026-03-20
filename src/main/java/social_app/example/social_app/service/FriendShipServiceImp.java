package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.FriendShipResponse;
import social_app.example.social_app.entity.FriendShipType;
import social_app.example.social_app.entity.FriendShips;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.repo.FriendShipRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendShipServiceImp implements FriendShipService{
    private final MemberService memberService;
    private final UserService userService;
    private final FriendShipRepository  friendShipRepository;

    @Override
    public boolean isHasPermission(Integer memberId) {
        log.info("Member Id>>> "+memberId);
        Members members = this.memberService.getMemberById(memberId).orElseThrow(()->new RuntimeException("Not found Member"));
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users currentUser = this.userService.findByUsername(currentUsername).orElseThrow(()->new RuntimeException("Not found user"));
        log.info("Current user id>>> "+currentUser.getId());

        return currentUser.getId().equals(members.getUser().getId());
    }

    @Override
    public FriendShipResponse sendRequest(Integer requesterId, Integer addresserId) {
        if(!this.isHasPermission(requesterId)){
            return FriendShipResponse
                    .builder()
                    .message("You do not have permission")
                    .status(4003)
                    .statusText("failed")
                    .build();
        }
        if(this.memberService.isExist(requesterId)&&this.memberService.isExist(addresserId)&&requesterId!=addresserId){
            Members requester = this.memberService.getMemberById(requesterId).orElseThrow(()->new RuntimeException("Not found"));
            Members addresser = this.memberService.getMemberById(addresserId).orElseThrow(()->new RuntimeException("Not found"));
            FriendShips friendShips = FriendShips
                    .builder()
                    .status(FriendShipType.PENDING)
                    .requester(requester)
                    .addresser(addresser)
                    .build();
            this.friendShipRepository.save(friendShips);

            return FriendShipResponse
                    .builder()
                    .status(2000)
                    .statusText("pending")
                    .message("Sent")
                    .build();
        }
        return FriendShipResponse
                .builder()
                .status(4000)
                .statusText("failed")
                .message("Member not exist")
                .build();

    }

    @Override
    public FriendShipResponse accept(Integer addresserId,Integer requesterId) {
            Optional<FriendShips> friendShip = this.friendShipRepository.findByAddresserIdAndRequesterId(addresserId,requesterId);
            if(!this.isHasPermission(addresserId)){
                return FriendShipResponse
                        .builder()
                        .message("You do not have permission")
                        .status(4003)
                        .statusText("failed")
                        .build();
            }
           if(friendShip.isPresent()){
               if(!friendShip.get().getStatus().equals(FriendShipType.PENDING)){
                   return FriendShipResponse
                           .builder()
                           .message("Request handled")
                           .status(4002)
                           .statusText("failed")
                           .build();
               }
               friendShip.get().setStatus(FriendShipType.ACCEPTED);
               friendShip.get().setId(friendShip.get().getId());
               this.friendShipRepository.save(friendShip.get());
               return FriendShipResponse
                       .builder()
                       .message("Accepted Successful")
                       .status(2000)
                       .statusText("accepted")
                       .build();
           }
           else{
               return FriendShipResponse
                       .builder()
                       .message("Not found request friend")
                       .status(4000)
                       .statusText("failed")
                       .build();
           }
    }

    @Override
    public FriendShipResponse denied(Integer addresserId, Integer requesterId) {
        Optional<FriendShips> friendShip = this.friendShipRepository.findByAddresserIdAndRequesterId(addresserId,requesterId);
        if(!this.isHasPermission(addresserId)){
            return FriendShipResponse
                    .builder()
                    .message("You do not have permission")
                    .status(4003)
                    .statusText("failed")
                    .build();
        }
        if(friendShip.isPresent()){
            if(!friendShip.get().getStatus().equals(FriendShipType.PENDING)){
                return FriendShipResponse
                        .builder()
                        .message("Request handled")
                        .status(4002)
                        .statusText("failed")
                        .build();
            }
            friendShip.get().setStatus(FriendShipType.DENIED);
            friendShip.get().setId(friendShip.get().getId());
            this.friendShipRepository.save(friendShip.get());
            return FriendShipResponse
                    .builder()
                    .message("Denied Successful")
                    .status(2000)
                    .statusText("denied")
                    .build();
        }
        else{
            return FriendShipResponse
                    .builder()
                    .message("Not found request friend")
                    .status(4000)
                    .statusText("failed")
                    .build();
        }
    }


}
