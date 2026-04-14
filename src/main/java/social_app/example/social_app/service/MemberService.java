package social_app.example.social_app.service;

import social_app.example.social_app.dto.MemberDetailResponse;
import social_app.example.social_app.dto.MemberResponse;
import social_app.example.social_app.dto.RegisterDTO;
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
