package social_app.example.social_app.service.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.auth.*;
import social_app.example.social_app.dto.usrAndMember.UserResponse;
import social_app.example.social_app.entity.RefreshToken;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.AuthException;
import social_app.example.social_app.security.JwtUtil;
import social_app.example.social_app.service.member.MemberService;
import social_app.example.social_app.service.usrRole.UserRoleService;
import social_app.example.social_app.service.usr.UserService;

import java.security.Principal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService {
    private final UserService userService;
    private final MemberService memberService;
    private final UserRoleService userRoleService;
    private final AuthenticationManager authenticationManager;
    private final TokenService  tokenService;
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
            // returned UserDetails
            //------------------Take user name to provide for create token-----------
            Users users = this.userService.findByUsername(request.getUsername());
            long accessExpirationMillis = 1000*60*10;
            long refreshExpirationMillis = 24*7*60*60*1000;
            String accessToken = this.jwtUtil.createToken(users.getUsername(),accessExpirationMillis);
            String refreshToken = this.jwtUtil.createToken(users.getUsername(),refreshExpirationMillis);
            //----------------Save refresh token to revoke----------
            Instant expired = Instant.now().plusMillis(refreshExpirationMillis);
            RefreshToken refreshTokenSave = RefreshToken.builder()
                    .refreshToken(refreshToken)
                    .users(users)
                    .expired(expired)
                    .build();
            this.tokenService.save(refreshTokenSave);
            return LoginResponse
                    .builder()
                    .memberId(users.getMember().getId())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .fullName(users.getMember().getFullName())
                    .role("member") // auto member, admin just only create direct by DB
                    .build();

        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new AuthException("User name or Password inCorrect");
        }
    }

    @Override
    public LogoutResponse logout(String refreshToken) {

       RefreshToken refreshTokenDb = this.tokenService.getRefreshToken(refreshToken);
       this.tokenService.remove(refreshTokenDb);
       return LogoutResponse.builder().message("Logout success").build();
    }

    @Override
    @Transactional
    public RefreshTokenResp refreshToken(RefreshTokenRequ request) {
        log.info(">>>INPUT TOKEN: "+request.getRefreshToken());
        boolean isValid = this.jwtUtil.isValidateToken(request.getRefreshToken());
        if(!isValid){
            throw new AuthException("Refresh token invalid or expired");
        }
       RefreshToken checkRefreshToken = this.tokenService.getRefreshToken(request.getRefreshToken()); // throw not found token exception
        log.info(">>>DB TOKEN: "+checkRefreshToken);
        long accessExpirationMillis = 1000*60*10;
        long refreshExpirationMillis = 24*7*60*60*1000;
        String username = this.jwtUtil.extractUsername(request.getRefreshToken());
        String accessToken = this.jwtUtil.createToken(username,accessExpirationMillis);
        String refreshToken = this.jwtUtil.createToken(username,refreshExpirationMillis);
        Instant expired = Instant.now().plusMillis(refreshExpirationMillis);

        //-----------Delete old refresh token--------------
//        this.tokenService.remove(checkRefreshToken);
        //----------Create and save new Refresh token------
//        Users user = this.userService.findByUsername(username);
//        RefreshToken refreshTokenDb = RefreshToken.builder()
//                .users(user)
//                .refreshToken(refreshToken)
//                .expired(expired)
//                .build();
        checkRefreshToken.setRefreshToken(refreshToken);
        checkRefreshToken.setExpired(expired);
        this.tokenService.save(checkRefreshToken);
        return RefreshTokenResp.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

}
