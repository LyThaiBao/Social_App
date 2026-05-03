package social_app.example.social_app.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.notification.NotificationRequest;
import social_app.example.social_app.dto.notification.NotificationResponse;

import social_app.example.social_app.service.notifi.NotificationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<List<NotificationResponse<?>>>> getAllNotifications(@RequestBody NotificationRequest request){
        log.info("ZOO"+request);
        List<NotificationResponse<?>> responseList = this.notificationService.getNotificationByMemberId(request.getMemberId());
        log.info("DATA: "+responseList);
        return ResponseEntity.ok(ApiResponse.success("Get all success",responseList));
    }
}
