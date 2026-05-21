package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.Likes;

import java.util.List;
import java.util.Set;

@Repository
public interface LikeRepository extends JpaRepository<Likes,Integer> {

//    @Query("select count (*) from Likes l where l.post.id = :postId and l.status = 'LIKED'")
//    Long countLikeOfPost(@Param("postId") Integer postId);

    @Query("select l from Likes l where l.member.id = :memberId and l.post.id = :postId")
    Likes getLikeByMemberIdAndPostId(@Param("memberId") Integer memberId,@Param("postId") Integer postId);

    @Query("select l.post.id from  Likes  l where l.member.id =:memberId and l.post.id in :postIds and l.status = LikeStatus.LIKED")
    Set<Integer> findLikedPostIds(@Param("memberId")Integer memberId, @Param("postIds") List<Integer> postIds);
}
