package social_app.example.social_app.service.usr;

import social_app.example.social_app.dto.auth.RegisterDTO;
import social_app.example.social_app.entity.Users;


public interface UserService {
    Users findByUsername(String username);
    Users findByUserId(Integer userId);
    boolean isExistName(String username);
    Users createUser(RegisterDTO registerInfo);
}
