package social_app.example.social_app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.LoginRequest;
import social_app.example.social_app.dto.LoginResponse;
import social_app.example.social_app.dto.RegisterDTO;
import social_app.example.social_app.dto.UserResponse;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.mapper.UserMapper;
import social_app.example.social_app.repo.MemberRepository;
import social_app.example.social_app.repo.UserRepository;
import social_app.example.social_app.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @Override
    @Transactional
    public UserResponse register(RegisterDTO registerInFo) {
       Users users = this.userMapper.convertToUser(registerInFo);
       Users userSaved  =  this.userRepository.save(users);
        Members members = Members
                .builder()
                .user(userSaved)
                .birthDay(registerInFo.getBirthDay())
                .fullName(registerInFo.getFullName())
                .build();
        this.memberRepository.save(members);
        return UserResponse.builder()
                .username(userSaved.getUsername())
                .createdAt(userSaved.getCreatedAt())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        try{
            Authentication authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));
            Users users = this.userRepository.findByUsername(request.getUsername()).orElseThrow(()->new RuntimeException("NOT FOUND"));
            System.out.println("RAW>>"+request.getPassword());
            System.out.println("ENCODE>>"+users.getPassword());
            boolean isCo = this.passwordEncoder.matches(request.getPassword(),users.getPassword());
            System.out.println("Ismatch "+isCo);
            String accessToken = this.jwtUtil.createToken(users.getUsername());
            return LoginResponse
                    .builder()
                    .accessToken(accessToken)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("User name or Password inCorrect");
        }
    }

}
