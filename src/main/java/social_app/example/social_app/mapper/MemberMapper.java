package social_app.example.social_app.mapper;

import org.springframework.stereotype.Component;
import social_app.example.social_app.dto.MemberDetailResponse;
import social_app.example.social_app.dto.MemberResponse;
import social_app.example.social_app.entity.Members;

@Component
public class MemberMapper {

    public  MemberResponse convertToMemberResponse(Members member){
        return MemberResponse.builder()
                .id(member.getId())
                .fullName(member.getFullName())
                .build();
    }

    public MemberDetailResponse convertToMemberDetail(Members members){
        return MemberDetailResponse.builder()
                .birthDay(members.getBirthDay())
                .fullName(members.getFullName())
                .id(members.getId())
                .joinDay(members.getCreateAt()) 
                .build();
    }
}
