package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.dto.ApiResponse;
import social_app.example.social_app.dto.MemberDetailResponse;
import social_app.example.social_app.dto.MemberResponse;
import social_app.example.social_app.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/search")
    public  ResponseEntity<ApiResponse<List<MemberResponse>>> search(@RequestParam String keyword){
        System.out.println(">>MEM CONTROLL key: "+keyword);
        List<MemberResponse> memberResponseList = this.memberService.search(keyword);
        return ResponseEntity.ok().body(ApiResponse.success("Get By Full Name Success",memberResponseList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberDetailResponse>> getMember(@PathVariable Integer id){
        MemberDetailResponse memberDetailResponse = this.memberService.getMemberDetail(id);
        return ResponseEntity.ok().body(ApiResponse.success("Get Success",memberDetailResponse));
    }
}
