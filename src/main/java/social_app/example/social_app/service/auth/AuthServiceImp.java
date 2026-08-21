package social_app.example.social_app.service.auth;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.auth.*;
import social_app.example.social_app.dto.usrAndMember.UserResponse;
import social_app.example.social_app.entity.CustomUserDetail;
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
    @Value("#{24*7*60*60*1000}")
    private Long expiredRefreshToken;
    @Value("#{1000*60*15}")
    private Long expiredAccessToken;
    @Override
    @Transactional
    public UserResponse register(RegisterDTO registerInFo) {
        if(this.userService.isExistName(registerInFo.getUsername())){
            throw new AuthException("User name was exist"); 
        }
        //----save User
        Users userSaved  =  this.userService.createUser(registerInFo);
        //----save member
        this.memberService.createMember(userSaved,registerInFo);
        //----Assign Default Role (Member) for User
        this.userRoleService.assignDefaultRole(userSaved);
        return UserResponse.builder()
                .username(userSaved.getUsername())
                .createdAt(userSaved.getCreatedAt())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try{
            //------------------Check user name / Password, if incorrect it will throw Except here--------------------------
            Authentication authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));// nhan vao principal and cridential
            //------------------Take user name to provide for create token-----------
            CustomUserDetail userDetail =(CustomUserDetail)authentication.getPrincipal();

            String accessToken = this.jwtUtil.createToken(userDetail.getUsername(),this.expiredAccessToken);
            String refreshToken = this.jwtUtil.createToken(userDetail.getUsername(),this.expiredRefreshToken);
            //----------------Save refresh token to revoke----------
            Instant expired = Instant.now().plusMillis(this.expiredRefreshToken);
            RefreshToken refreshTokenSave = RefreshToken.builder()
                    .refreshToken(refreshToken)
                    .users(userDetail.getUser())
                    .expired(expired)
                    .build();
            this.tokenService.save(refreshTokenSave);
            return LoginResponse
                    .builder()
                    .memberId(userDetail.getUser().getMember().getId())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .fullName(userDetail.getUser().getMember().getFullName())
                    .roles(userDetail.getRoles())
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
        boolean isValid = this.jwtUtil.isValidateToken(request.getRefreshToken());
        if(!isValid){
            throw new AuthException("Refresh token invalid or expired");
        }
       RefreshToken checkRefreshToken = this.tokenService.getRefreshToken(request.getRefreshToken()); // throw not found token exception
        String username = this.jwtUtil.extractUsername(request.getRefreshToken());
        String accessToken = this.jwtUtil.createToken(username,this.expiredAccessToken);
        String refreshToken = this.jwtUtil.createToken(username,this.expiredRefreshToken);
        // vua tao moi vua cap nhat nen xem lai
        Instant expired = Instant.now().plusMillis(this.expiredRefreshToken);
        checkRefreshToken.setRefreshToken(refreshToken);
        checkRefreshToken.setExpired(expired);

        Users user = this.userService.findByUsername(username);
        RefreshToken newRefreshToken = RefreshToken.builder()
                .expired(expired)
                .refreshToken(refreshToken)
                .users(user)
                .build();

        this.tokenService.save(newRefreshToken);
        return RefreshTokenResp.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

}
