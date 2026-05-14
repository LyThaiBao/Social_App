package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.Posts;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts,Integer>{

    List<Posts> findAllByMemberId(Integer memberId);

}
