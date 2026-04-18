package social_app.example.social_app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.FriendShipDetail;
import social_app.example.social_app.dto.FriendShipResponse;
import social_app.example.social_app.entity.FriendShipType;
import social_app.example.social_app.entity.FriendShips;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.exception.SentFriendShipException;
import social_app.example.social_app.mapper.FriendShipMapper;
import social_app.example.social_app.repo.FriendShipRepository;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendShipServiceImp implements FriendShipService{
    private final MemberService memberService;
    private final UserService userService;
    private final FriendShipMapper friendShipMapper;
    private final FriendShipRepository  friendShipRepository;

    @Override
    public FriendShips createFriendShip(FriendShipType status,  Integer requesterId,Integer addresserId) {
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

    private FriendShips getFriendShipsSingleWay(Integer requesterId, Integer addresserId) {
        return  this.friendShipRepository.findByRequesterIdAndAddresserId(requesterId,addresserId)
                .orElse(null);

    }



    @Override
    public FriendShips findByRequesterIdAndAddresserId(Integer requesterId, Integer addresserId) {
        return getFriendShipsSingleWay(requesterId, addresserId);

    }


    @Override
    public FriendShipDetail findBothId(Integer addresserId, Integer requesterId) {
        FriendShips c1 = this.getFriendShipsSingleWay(requesterId,addresserId);
        FriendShips c2 = this.getFriendShipsSingleWay(addresserId,requesterId);
        log.info("C1: "+c1);
        log.info("C2: "+c2);
        if(c1 != null){
            return this.friendShipMapper.convertToFriendShipDetail(c1);
        }
        if(c2 != null){
            return this.friendShipMapper.convertToFriendShipDetail(c2);
        }
        return  FriendShipDetail.builder()
                .friendShipType(null)
                .id(null)
                .addresserId(null)
                .requesterId(null)
                .build();

    }

    @Override
    @Transactional
    public FriendShipResponse sendRequest(Integer requesterId, Integer addresserId,Principal principal) {
        log.info(("HERE"));
        if(!this.isHasPermission(requesterId)){
            throw new SentFriendShipException("You do not have permission");
        }

        if(!this.memberService.isExist(requesterId)||!this.memberService.isExist(addresserId) ){
            throw new SentFriendShipException("Member not exist");
        }

        if(Objects.equals(addresserId, requesterId)){
            throw new SentFriendShipException("Can not send request to yourself");
        }

        FriendShipDetail friendShipDetail = this.findBothId(requesterId,addresserId);
        if(friendShipDetail.getId() == null){
            FriendShips friendShip = this.createFriendShip(FriendShipType.PENDING,requesterId,addresserId);
        }

        if(friendShipDetail.getFriendShipType()!=null && friendShipDetail.getFriendShipType().equals(FriendShipType.DENIED)){

          String username = principal.getName();
          Users user = this.userService.findByUsername(username);
            log.info(">>>> MemID: "+user.getMember().getId());
            log.info(">>> AddrID: "+friendShipDetail.getAddresserId());
            FriendShips friendShip = this.getFriendShipsSingleWay(friendShipDetail.getRequesterId(),friendShipDetail.getAddresserId());
          if(Objects.equals(user.getMember().getId(),friendShipDetail.getAddresserId())){
              log.info("Addresser: "+addresserId);
             Members addresser =  this.memberService.getMemberById(addresserId);
             Members requester = this.memberService.getMemberById(requesterId);
                friendShip.setRequester(requester);
                friendShip.setAddresser(addresser);
          }

          friendShip.setStatus(FriendShipType.PENDING);
          this.friendShipRepository.save(friendShip);
        }

        //-----return information------
        return FriendShipResponse
                .builder()
                .statusText("pending")
                .message("Sent")
                .build();
    }


@Override
public FriendShipResponse accept(Integer addresserId,Integer requesterId) {
    FriendShips friendShip = this.findByRequesterIdAndAddresserId(requesterId,addresserId);
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
    FriendShips friendShip = this.findByRequesterIdAndAddresserId(requesterId,addresserId);
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

    @Override
    public FriendShipResponse unFriend(Integer requesterId, Integer addresserId) {
        FriendShipDetail detail = this.findBothId(addresserId,requesterId);
        FriendShips friendShip =  this.findByRequesterIdAndAddresserId(detail.getRequesterId(),detail.getAddresserId());
        if(friendShip == null){
            throw  new NotFoundResource("Not found friendship");
        }
        if(friendShip.getStatus() != FriendShipType.ACCEPTED){
            throw new SentFriendShipException("Can not un friend because current status is not a friend");
        }
        this.friendShipRepository.delete(friendShip);
        return FriendShipResponse.builder()
                .message("unFriend success")
                .statusText("unFriend")
                .build();

    }

    @Override
    public FriendShipResponse cancelRequest(Integer requesterId, Integer addresserId) {
        if(!this.isHasPermission(requesterId)){
            throw new SentFriendShipException("You do not have permission");
        }

        FriendShips friendShip =  this.findByRequesterIdAndAddresserId(requesterId,addresserId);
        if(friendShip == null){
            throw  new NotFoundResource("Not found friendship");
        }
        if(friendShip.getStatus() != FriendShipType.PENDING){
            throw new SentFriendShipException("Can not cancel request because current status is not pending");
        }
        this.friendShipRepository.delete(friendShip);
        return FriendShipResponse.builder()
                .message("cancel request success")
                .statusText("cancel request")
                .build();

    }

}

