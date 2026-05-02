package social_app.example.social_app.service.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import social_app.example.social_app.entity.Roles;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.repo.RoleRepository;


@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Roles getRoleEntityByName(String roleName) {
        return  this.roleRepository.findByRoleName("ROLE_MEMBER").orElseThrow(()-> new NotFoundResource("Not found role valid"));
    }
}
