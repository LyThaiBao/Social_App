package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.FriendShipDetail;
import social_app.example.social_app.dto.FriendShipRequest;
import social_app.example.social_app.dto.FriendShipResponse;
import social_app.example.social_app.service.FriendShipService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friendship")
public class FriendShipController {

    private final FriendShipService friendShipService;
    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('MEMBER')")
    public ResponseEntity<ApiResponse<FriendShipResponse>> sendRequest(@RequestBody FriendShipRequest request, Principal principal){
        FriendShipResponse result = this.friendShipService.sendRequest(request.getRequesterId(),request.getAddresserId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Sent Success",result));

    }
    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/accept")
    public ResponseEntity<ApiResponse<FriendShipResponse>> accept(@RequestBody FriendShipRequest request){
        FriendShipResponse result = this.friendShipService.accept(request.getAddresserId(),request.getRequesterId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Accepted",result));
    }

    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/denied")
    public ResponseEntity<ApiResponse<FriendShipResponse>> denied(@RequestBody FriendShipRequest request){
        FriendShipResponse result = this.friendShipService.denied(request.getAddresserId(),request.getRequesterId());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Denied",result));
    }

    @GetMapping("/bothId")
    public ResponseEntity<ApiResponse<FriendShipDetail>> getByBothID(@RequestBody FriendShipRequest request){
        FriendShipDetail friendShipDetail = this.friendShipService.findBothId(request.getAddresserId(), request.getRequesterId());
        return ResponseEntity.ok().body(ApiResponse.success("Get Success",friendShipDetail));
    }
}
