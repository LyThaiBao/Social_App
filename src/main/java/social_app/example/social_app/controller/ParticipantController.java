package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.ParticipantResponse;
import social_app.example.social_app.entity.Participants;
import social_app.example.social_app.service.ParticipantService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/participants")
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;

//    @GetMapping
//    public ResponseEntity<ApiResponse<List<ParticipantResponse>>> getAll(Principal principal){
//        List<Participants> participantResponses = this.participantService.getAllParticipant(principal);
//        return  ResponseEntity.ok().body(ApiResponse.success("Get participants success",participantResponses));
//    }



}
