package social_app.example.social_app.service;

import org.springframework.stereotype.Service;
import social_app.example.social_app.entity.Users;

import java.util.Optional;


public interface UserService {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByUserId(Integer userId);
}
