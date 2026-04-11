package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.RegisterDTO;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.mapper.UserMapper;
import social_app.example.social_app.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService{
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Override
    public Users findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(()-> new NotFoundResource("Not found user"));
    }

    @Override
    public Users findByUserId(Integer userId) {
        return this.userRepository.findById(userId).orElseThrow(()-> new NotFoundResource("Not found member"));
    }

    @Override
    public boolean isExistName(String username) {
        Optional<Users> user = this.userRepository.findByUsername(username);
        return user.isPresent();
    }

    @Override
    public Users createUser(RegisterDTO registerInfo) {
        Users user = this.userMapper.convertToUser(registerInfo);
        this.userRepository.save(user);
        return user;
    }
}
