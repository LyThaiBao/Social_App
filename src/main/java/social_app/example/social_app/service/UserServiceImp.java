package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    @Override
    public Optional<Users> findByUsername(String username) {
        Optional<Users> users = this.userRepository.findByUsername(username);
        return users;
    }

    @Override
    public Optional<Users> findByUserId(Integer userId) {
        return this.userRepository.findById(userId);
    }
}
