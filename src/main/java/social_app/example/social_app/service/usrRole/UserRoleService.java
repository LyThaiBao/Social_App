package social_app.example.social_app.service.usrRole;

import social_app.example.social_app.entity.UserRoles;
import social_app.example.social_app.entity.Users;

public interface UserRoleService {
    UserRoles assignDefaultRole(Users userSaved);
}
