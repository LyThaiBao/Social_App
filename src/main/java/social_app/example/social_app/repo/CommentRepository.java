package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import social_app.example.social_app.entity.Comments;
import social_app.example.social_app.entity.Posts;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments,Integer> {
    @Modifying
    @Query("update Comments c set c.isDeleted = true where c.post.id = :postId and c.id = :commentId and c.isDeleted = false")
    int deleteComment(@Param("postId")Integer postId, @Param("commentId")Integer commentId);

    @Query("select c from Comments c where c.post.id = :postId and c.isDeleted = false ")
    List<Comments> getAllByPostId(@Param("postId") Integer postId);
}
