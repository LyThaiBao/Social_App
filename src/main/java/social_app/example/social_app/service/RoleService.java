package social_app.example.social_app.service;

import social_app.example.social_app.entity.Roles;

public interface RoleService {
    Roles getRoleEntityByName(String roleName);
}
