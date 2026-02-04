package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    @Override
    public Users createUser(Users users) {
    this.userRepository.save(users);
    return users;
    }
}
