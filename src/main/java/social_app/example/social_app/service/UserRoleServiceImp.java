package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.entity.Roles;
import social_app.example.social_app.entity.UserRoles;
import social_app.example.social_app.entity.Users;
import social_app.example.social_app.repo.RoleRepository;
import social_app.example.social_app.repo.UserRoleRepository;


@Service
@RequiredArgsConstructor
public class UserRoleServiceImp implements UserRoleService{

    private final RoleService roleService;
    private final UserRoleRepository userRoleRepository;
    @Override
    public UserRoles assignDefaultRole(Users users) {
        Roles role = this.roleService.getRoleEntityByName("ROLE_MEMBER");
        UserRoles userRoles = UserRoles.builder()
                .user(users)
                .role(role)
                .build();
       this.userRoleRepository.save(userRoles);
       return userRoles;
    }
}
