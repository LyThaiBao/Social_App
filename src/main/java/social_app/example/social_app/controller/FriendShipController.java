package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.friendship.FriendShipDetail;
import social_app.example.social_app.dto.friendship.FriendShipRequest;
import social_app.example.social_app.dto.friendship.FriendShipResponse;
import social_app.example.social_app.dto.notification.FriendRequest;
import social_app.example.social_app.dto.notification.NotificationResponse;
import social_app.example.social_app.service.friendship.FriendShipService;
import social_app.example.social_app.service.notifi.NotificationService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friendship")
@Slf4j
public class FriendShipController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final FriendShipService friendShipService;
    private final NotificationService notificationService;
    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('MEMBER')")
    public ResponseEntity<ApiResponse<FriendShipResponse>> sendRequest(@RequestBody FriendShipRequest request,Principal principal){
        log.info("SEND >>>");
        log.info(">>> Request: "+request);
        FriendShipResponse result = this.friendShipService.sendRequest(request.getRequesterId(),request.getAddresserId(),principal);
        //--------------create notification and send to user-------------
        NotificationResponse<?> notificationResponse = this.notificationService.friendRequest(result);
        this.simpMessagingTemplate.convertAndSend("/queue/notification", notificationResponse);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Sent Success",result));

    }
    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/accept")
    public ResponseEntity<ApiResponse<FriendShipResponse>> accept(@RequestBody FriendShipRequest request){
        FriendShipResponse result = this.friendShipService.accept(request.getAddresserId(),request.getRequesterId());
        //--------------create notification and send to user-------------
        NotificationResponse<?> notificationResponse = this.notificationService.friendAccepted(result);
        this.simpMessagingTemplate.convertAndSend("/queue/notification", notificationResponse);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Accepted",result));
    }

    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/denie")
    public ResponseEntity<ApiResponse<FriendShipResponse>> denied(@RequestBody FriendShipRequest request){
        System.out.println(">>> Request: "+3);

        FriendShipResponse result = this.friendShipService.denied(request.getAddresserId(),request.getRequesterId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Denied",result));
    }

    @PostMapping("/bothId")
    public ResponseEntity<ApiResponse<FriendShipDetail>> getByBothID(@RequestBody FriendShipRequest request){
        log.info("BOTH ID: "+request);
        FriendShipDetail friendShipDetail = this.friendShipService.findBothId(request.getAddresserId(), request.getRequesterId());
        return ResponseEntity.ok().body(ApiResponse.success("Get Success",friendShipDetail));
    }

    @PostMapping("/unfriend")
    public ResponseEntity<ApiResponse<FriendShipResponse>> unfriend(@RequestBody FriendShipRequest request){
        System.out.println(">>> Request: "+5);

        FriendShipResponse response = this.friendShipService.unFriend(request.getRequesterId(), request.getAddresserId());
        return ResponseEntity.ok().body(ApiResponse.success("unFriend Success",response));
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponse<FriendShipResponse>> cancelRequest(@RequestBody FriendShipRequest request){
        FriendShipResponse response = this.friendShipService.cancelRequest(request.getRequesterId(), request.getAddresserId());
        return ResponseEntity.ok().body(ApiResponse.success("Cancel request success",response));
    }
}
