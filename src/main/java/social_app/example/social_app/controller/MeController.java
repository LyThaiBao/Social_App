package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.MeResponse;
import social_app.example.social_app.service.MeService;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping("/api/me")
@RequiredArgsConstructor
public class MeController {

    private  final MeService meService;

    @GetMapping
    public ResponseEntity<ApiResponse<MeResponse>> getMe(Principal principal){
        log.info("GET HERE");
        MeResponse meResponse = this.meService.getMe(principal);
        return ResponseEntity.ok().body(ApiResponse.success("Get Me Success",meResponse));
    }
}
