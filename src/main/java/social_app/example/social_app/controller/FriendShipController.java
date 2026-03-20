package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.FriendShipRequest;
import social_app.example.social_app.dto.FriendShipResponse;
import social_app.example.social_app.service.FriendShipService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friendships")
public class FriendShipController {

    private final FriendShipService friendShipService;
    @PostMapping("/send")
    @PreAuthorize("hasAnyRole('MEMBER')")
    public ResponseEntity<ApiResponse<FriendShipResponse>> sendRequest(@RequestBody FriendShipRequest request){
        FriendShipResponse result = this.friendShipService.sendRequest(request.getRequesterId(),request.getAddresserId());
        boolean isSuccess = result.getStatus() == 2000;
        ApiResponse<FriendShipResponse> response = ApiResponse.<FriendShipResponse>builder()
                .status(isSuccess?2000:4000)
                .message(isSuccess?"Send Successful":"Send Failed")
                .body(result)
                .build();
        return isSuccess?ResponseEntity.ok(response):ResponseEntity.badRequest().body(response);

    }
    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/accept")
    public ResponseEntity<ApiResponse<FriendShipResponse>> accept(@RequestBody FriendShipRequest request){
        FriendShipResponse result = this.friendShipService.accept(request.getAddresserId(),request.getRequesterId());
        boolean isSuccess = result.getStatus() == 2000;
        ApiResponse<FriendShipResponse> response = ApiResponse.<FriendShipResponse>builder()
                .status(isSuccess?2000:4000)
                .message(isSuccess?"Accept Successful":"Accept Failed")
                .body(result)
                .build();
        return isSuccess?ResponseEntity.ok(response):ResponseEntity.badRequest().body(response);


    }

    @PreAuthorize("hasAnyRole('MEMBER')")
    @PostMapping("/denied")
    public ResponseEntity<ApiResponse<FriendShipResponse>> denied(@RequestBody FriendShipRequest request){
        FriendShipResponse result = this.friendShipService.denied(request.getAddresserId(),request.getRequesterId());
        boolean isSuccess = result.getStatus() == 2000;
        ApiResponse<FriendShipResponse> response = ApiResponse.<FriendShipResponse>builder()
                .status(isSuccess?2000:4000)
                .message(isSuccess?"Denied Successful":"Denied Failed")
                .body(result)
                .build();
        return isSuccess?ResponseEntity.ok(response):ResponseEntity.badRequest().body(response);

    }
}
