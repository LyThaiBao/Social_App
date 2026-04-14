package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.Members;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, Integer> {
    Optional<Members> getMemberByFullName(String fullName);
    List<Members> getByUserUsername(String userUsername);
    List<Members> getByFullName(String fullName);
}
