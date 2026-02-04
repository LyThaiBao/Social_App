package social_app.example.social_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.service.UserService;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping({"/",""})
    public ResponseEntity<Users> createUser(@RequestBody Users users){
        Users usersDb = this.userService.createUser(users);
        return ResponseEntity.ok(usersDb);
    }
}
