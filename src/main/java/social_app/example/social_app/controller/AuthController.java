package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import social_app.example.social_app.dto.*;
import social_app.example.social_app.dto.auth.*;
import social_app.example.social_app.dto.usrAndMember.UserResponse;
import social_app.example.social_app.service.auth.AuthService;

import java.net.URI;
import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody RegisterDTO registerInFo){
        UserResponse userResponse = this.authService.register(registerInFo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()// Lấy path hiện tại (ví dụ /api/auth)
                .path("/{username}") // tao them đuôi
                .buildAndExpand(userResponse.getUsername())// truyền value cho đuôi
                .toUri();
        return ResponseEntity.created(location).body(ApiResponse.success("Create Successful",userResponse));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest){
        log.info(">>>LOGIN: "+loginRequest);
        LoginResponse response = this.authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Login Success",response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<LogoutResponse>> logout(@RequestBody LogoutRequest request){
        LogoutResponse response = this.authService.logout(request.getRefreshToken());
        return ResponseEntity.ok().body(ApiResponse.success("Logout success",response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResp>> refreshToken(@RequestBody RefreshTokenRequ request){
        log.info("CALL REFRESH >>>> ");
        RefreshTokenResp response = this.authService.refreshToken(request);
        return  ResponseEntity.ok().body(ApiResponse.success("Refresh success",response));
    }


}
