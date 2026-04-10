package social_app.example.social_app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.RegisterDTO;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.repo.MemberRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService{
    private final MemberRepository memberRepository;

    @Override
    public boolean isExist(Integer id) {
        Optional<Members> member = this.memberRepository.findById(id);
        return member.isPresent();
    }

    @Override
    public Members getMemberById(Integer id) {
        return this.memberRepository.findById(id).orElseThrow(()-> new NotFoundResource("Not found user"));

    }

    @Override
    public Optional<Members> getMemberByFullName(String fullName) {
        Optional<Members> member = this.memberRepository.getMemberByFullName(fullName);
        return member;
    }

    @Override
    public Members createMember(Users userSaved, RegisterDTO registerInfo) {
        Members member = Members.builder()
                .user(userSaved)
                .birthDay(registerInfo.getBirthDay())
                .fullName(registerInfo.getFullName())
                .build();
        this.memberRepository.save(member);
        return member;
    }
}
