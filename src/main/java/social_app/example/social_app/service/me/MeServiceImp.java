package social_app.example.social_app.service.me;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.usrAndMember.MeResponse;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.AuthException;
import social_app.example.social_app.service.usr.UserService;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class MeServiceImp implements MeService {

    private  final UserService userService;
    @Override
    public MeResponse getMe(Principal principal) {
        String username = principal.getName();
        if(username!=null){
            Users user = this.userService.findByUsername(username);
            return MeResponse.builder()
                    .memberId(user.getMember().getId())
                    .username(username)
                    .birthDay(user.getMember().getBirthDay())
                    .fullName(user.getMember().getFullName())
                    .build();
        }
        else{
            throw new AuthException("Vui lòng đăng nhập");
        }
    }
}
