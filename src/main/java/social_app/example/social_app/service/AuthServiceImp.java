package social_app.example.social_app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.LoginRequest;
import social_app.example.social_app.dto.LoginResponse;
import social_app.example.social_app.dto.RegisterDTO;
import social_app.example.social_app.dto.UserResponse;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.AuthException;
import social_app.example.social_app.security.JwtUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService{
    private final UserService userService;
    private final MemberService memberService;
    private final UserRoleService userRoleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @Override
    @Transactional
    public UserResponse register(RegisterDTO registerInFo) {
        if(this.userService.isExistName(registerInFo.getUsername())){
            throw new AuthException("User name was exist");
        }
        //----Convert user data Map with DB and save
        Users userSaved  =  this.userService.createUser(registerInFo);
        //----Convert member data Map with DB and save
        this.memberService.createMember(userSaved,registerInFo);
        //----AssignDefaultRole for User
        this.userRoleService.assignDefaultRole(userSaved);
        return UserResponse.builder()
                .username(userSaved.getUsername())
                .createdAt(userSaved.getCreatedAt())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try{

            //------------------Check user name / Password --------------------------
            Authentication authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
            //------------------Take user name to provide for create token-----------
            Users users = this.userService.findByUsername(request.getUsername());
            String accessToken = this.jwtUtil.createToken(users.getUsername());
            return LoginResponse
                    .builder()
                    .accessToken(accessToken)
                    .build();

        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new AuthException("User name or Password inCorrect");
        }
    }

}
