package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import social_app.example.social_app.entity.UserRoles;

public interface UserRoleRepository extends JpaRepository<UserRoles,Integer> {

}
