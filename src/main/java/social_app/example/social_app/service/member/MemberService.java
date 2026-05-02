package social_app.example.social_app.service.member;

import social_app.example.social_app.dto.usrAndMember.MemberDetailResponse;
import social_app.example.social_app.dto.usrAndMember.MemberResponse;
import social_app.example.social_app.dto.auth.RegisterDTO;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Users;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    public boolean isExist(Integer id);
    Members getMemberById(Integer id);
    public Optional<Members> getMemberByFullName(String fullName);
    Members createMember(Users userSaved, RegisterDTO registerInfo);
    MemberDetailResponse getMemberDetail(Integer id);
    List<MemberResponse> search(String keyword);

}
