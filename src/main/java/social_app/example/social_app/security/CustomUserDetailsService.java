package social_app.example.social_app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.repo.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = this.userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("Not found User"));
        List<SimpleGrantedAuthority> authorities = users.getUserRoles().stream().map(userRoles -> {
          String roleName = userRoles.getRole().getRoleName();
          return  new SimpleGrantedAuthority(roleName);
        }).toList();
        return User.withUsername(users.getUsername())
                .password(users.getPassword())
                .authorities(authorities)
                .disabled(!users.isEnable())
                .build()
                ;
    }
}
