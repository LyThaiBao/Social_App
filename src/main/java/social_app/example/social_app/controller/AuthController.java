package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import social_app.example.social_app.dto.*;
import social_app.example.social_app.service.AuthService;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody RegisterDTO registerInFo){
        UserResponse userResponse = this.authService.register(registerInFo);
        ApiResponse<UserResponse> apiResponse = ApiResponse.<UserResponse>
                builder()
                .message("Create Successful")
                .status(1000)
                .body(userResponse)
                .build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()// Lấy path hiện tại (ví dụ /api/auth)
                .path("/{username}") // tao them đuôi
                .buildAndExpand(userResponse.getUsername())// truyền value cho đuôi
                .toUri();
        return ResponseEntity.created(location).body(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        System.out.println("Login>>>"+loginRequest);
        LoginResponse response = this.authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

}
