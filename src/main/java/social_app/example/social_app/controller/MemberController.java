package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.usrAndMember.MemberDetailResponse;
import social_app.example.social_app.dto.usrAndMember.MemberResponse;
import social_app.example.social_app.service.member.MemberService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/search")
    public  ResponseEntity<ApiResponse<List<MemberResponse>>> search(@RequestParam String keyword){
        log.info(">>>SEARCH: "+keyword);
        List<MemberResponse> memberResponseList = this.memberService.search(keyword);
        return ResponseEntity.ok().body(ApiResponse.success("Get By Full Name Success",memberResponseList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDetailResponse>> getMember(@PathVariable Integer id){
        log.info("ALO");
        MemberDetailResponse memberDetailResponse = this.memberService.getMemberDetail(id);
        return ResponseEntity.ok().body(ApiResponse.success("Get Success",memberDetailResponse));
    }
}
