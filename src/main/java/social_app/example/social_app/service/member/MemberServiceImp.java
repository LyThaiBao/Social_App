package social_app.example.social_app.service.member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.usrAndMember.MemberDetailResponse;
import social_app.example.social_app.dto.usrAndMember.MemberResponse;
import social_app.example.social_app.dto.auth.RegisterDTO;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.mapper.MemberMapper;
import social_app.example.social_app.repo.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImp implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
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

    @Override
    public MemberDetailResponse getMemberDetail(Integer id) {
        Members member = this.getMemberById(id);
        return  this.memberMapper.convertToMemberDetail(member);
    }


    @Override
    public List<MemberResponse> search(String keyword) {
        System.out.println(">>> KEY WORD: "+keyword);
        List<MemberResponse> memberResponseList = new ArrayList<>() ;

//        String keyClean = keyword.contains("@") ? keyword.substring(1):keyword;
        if(keyword.contains("@")){
            String keyClean =  keyword.substring(1);
            memberResponseList=  this.memberRepository.getByUserUsername(keyClean).stream().map(this.memberMapper::convertToMemberResponse).toList();
        }
        else{
            memberResponseList= this.memberRepository.getByFullName(keyword).stream().map(this.memberMapper::convertToMemberResponse).toList();

        }
        return memberResponseList;
    }
}
