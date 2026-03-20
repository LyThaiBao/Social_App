package social_app.example.social_app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.entity.Members;
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
    public Optional<Members> getMemberById(Integer id) {
        Optional<Members> member = this.memberRepository.findById(id);
        return member;
    }

    @Override
    public Optional<Members> getMemberByFullName(String fullName) {
        Optional<Members> member = this.memberRepository.getMemberByFullName(fullName);
        return member;
    }
}
