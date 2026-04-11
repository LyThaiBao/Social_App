package social_app.example.social_app.service;

import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.RegisterDTO;
import social_app.example.social_app.entity.Users;

import java.util.Optional;


public interface UserService {
    Users findByUsername(String username);
    Users findByUserId(Integer userId);
    boolean isExistName(String username);
    Users createUser(RegisterDTO registerInfo);
}
