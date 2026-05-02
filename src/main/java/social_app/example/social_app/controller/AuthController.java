package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import social_app.example.social_app.dto.*;
import social_app.example.social_app.dto.auth.LoginRequest;
import social_app.example.social_app.dto.auth.LoginResponse;
import social_app.example.social_app.dto.auth.RegisterDTO;
import social_app.example.social_app.dto.usrAndMember.UserResponse;
import social_app.example.social_app.service.auth.AuthService;

import java.net.URI;

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
        LoginResponse response = this.authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Login Success",response));
    }

}
