package social_app.example.social_app.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import social_app.example.social_app.entity.CustomUserDetail;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.repo.UserRepository;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        Users users = this.userRepository.findByUsername(username).orElseThrow(()->new NotFoundResource("Not found user"));
        return new CustomUserDetail(users);
    }
}
