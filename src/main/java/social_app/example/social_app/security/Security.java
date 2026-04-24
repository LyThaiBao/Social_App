package social_app.example.social_app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class Security {
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity){

        httpSecurity.csrf(csrf->csrf.disable())
                .cors(c -> c.configurationSource(corsConfigurationSource()))
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(configure->{
                   configure.requestMatchers("/api/auth/**").permitAll()
                           .requestMatchers("/ws/**","/ws/info/**").permitAll()
                           .requestMatchers(HttpMethod.POST,"/api/friendship/**").authenticated()
                           .requestMatchers(HttpMethod.GET,"/api/me/**").permitAll()
                           .requestMatchers(HttpMethod.GET,"/api/members/**").authenticated()
                           .requestMatchers(HttpMethod.POST,"/api/conversations/**").authenticated()
                           .requestMatchers(HttpMethod.GET,"/api/conversations/**").authenticated()
                           .requestMatchers(HttpMethod.POST,"/api/messages/**").authenticated()
                           .requestMatchers(HttpMethod.GET,"/api/messages/**").authenticated()
                           .requestMatchers(HttpMethod.POST,"/api/cloud/**").authenticated();
                })

                // use JwtAuthenticationEntryPoint to handle exception outside controller
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
        ;
        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration){
        return  configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Set tất cả nguồn gốc được phép
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));

        // Set tất cả phương thức
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Set tất cả header
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Cho phép gửi credential (cookies, auth headers)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
