package social_app.example.social_app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.LoginRequest;
import social_app.example.social_app.dto.LoginResponse;
import social_app.example.social_app.dto.RegisterDTO;
import social_app.example.social_app.dto.UserResponse;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Roles;
import social_app.example.social_app.entity.UserRoles;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.AuthException;
import social_app.example.social_app.mapper.UserMapper;
import social_app.example.social_app.repo.MemberRepository;
import social_app.example.social_app.repo.RoleRepository;
import social_app.example.social_app.repo.UserRepository;
import social_app.example.social_app.repo.UserRoleRepository;
import social_app.example.social_app.security.JwtUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImp implements AuthService{
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @Override
    @Transactional
    public UserResponse register(RegisterDTO registerInFo) {
        //Convert user data Map with DB and save
       Users users = this.userMapper.convertToUser(registerInFo);
       Users userSaved  =  this.userRepository.save(users);
       // Convert member data Map with DB and save
        Members members = Members
                .builder()
                .user(userSaved)
                .birthDay(registerInFo.getBirthDay())
                .fullName(registerInFo.getFullName())
                .build();
        this.memberRepository.save(members);
        //Default Role
        Roles role = this.roleRepository.findByRoleName("ROLE_MEMBER").orElseThrow(()->new RuntimeException("Not found role"));
        //Link Role
        UserRoles userRoles = UserRoles.builder().user(userSaved).role(role).build();
        this.userRoleRepository.save(userRoles);
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
            log.info("Authentication >> {}",authentication);
            Users users = this.userRepository.findByUsername(request.getUsername()).orElseThrow(()->new RuntimeException("NOT FOUND"));
            String accessToken = this.jwtUtil.createToken(users.getUsername());
            return LoginResponse
                    .builder()
                    .accessToken(accessToken)
                    .build();

        } catch (BadCredentialsException |InternalAuthenticationServiceException e) {
            throw new AuthException("User name or Password inCorrect");
        }
    }

}
