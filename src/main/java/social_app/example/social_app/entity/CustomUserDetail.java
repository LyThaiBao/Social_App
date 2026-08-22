package social_app.example.social_app.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
@Getter // return my Users
public class CustomUserDetail implements UserDetails {
    private final Users user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // dung cho Security cua spring authenticate
            return this.getRoles().stream().map(SimpleGrantedAuthority::new).toList();
    }

    public List<String> getRoles(){ // response to client
        return this.user.getUserRoles().stream().map(userRole -> userRole.getRole().getRoleName()).toList();
    }
    @Override
    public @Nullable String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnable();
    }



}
