package social_app.example.social_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.comments.CommentLine;
import social_app.example.social_app.entity.Roles;
import social_app.example.social_app.repo.RoleRepository;

@RequiredArgsConstructor
@Component
public class InitRole implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        if(!this.roleRepository.findByRoleName("ROLE_MEMBER").isPresent()){
            this.roleRepository.save(Roles.builder().roleName("ROLE_MEMBER").build());
        }
        if(!this.roleRepository.findByRoleName("ROLE_ADMIN").isPresent()){
            this.roleRepository.save(Roles.builder().roleName("ROLE_ADMIN").build());

        }
    }
}
