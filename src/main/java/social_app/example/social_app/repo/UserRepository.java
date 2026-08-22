package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.Users;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    @Query("select distinct u from Users u join fetch u.member " +
            "join fetch u.userRoles ur join fetch ur.role where u.username =:username")
    Optional<Users> findByUsername(@Param("username") String username);

}
