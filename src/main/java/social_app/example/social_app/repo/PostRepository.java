package social_app.example.social_app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.dto.post.PostRequest;
import social_app.example.social_app.entity.Posts;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Posts,Integer>{
    @Query("select p from Posts p where p.member.id = :myId")
    List<Posts> findAllByMemberId(@Param("myId") Integer memberId);

    @Query("select p from Posts p where p.member.id = :myId and p.status != 'DELETED' or " +
            "p.status = 'PUBLIC' or " +
            "((p.member.id in (select f.requester.id from FriendShips f where f.addresser.id = :myId and f.status = 'ACCEPTED') or " +
            "p.member.id in (select f.addresser.id from FriendShips  f where f.requester.id = :myId and f.status = 'ACCEPTED' )) and " +
            "p.status not in ('DELETED','PRIVATE')) order by p.createdAt desc ")
    Page<Posts> findNewsfeedPosts(@Param("myId") Integer myId, Pageable pageable);// auto set limit, size we just give pageable

    @Modifying
    @Query("update Posts p set p.status = 'DELETED' where p.id = :id and p.member.id=:memberId and p.status != 'DELETED'")
    int softDelete(@Param("id") Integer id,@Param("memberId") Integer memberId);

    @Query("select p.totalLikes from Posts  p where p.id =:postId")
    Long getTotalLikes(@Param("postId") Integer postId);

    @Modifying
    @Query("update Posts  p set p.totalLikes=(p.totalLikes+1) where p.id = :postId")
    int incrementLike(@Param("postId") Integer postId);

    @Modifying
    @Query("update Posts  p set p.totalLikes=(p.totalLikes-1) where p.id = :postId")
    int reduceLike(@Param("postId") Integer postId);

}
