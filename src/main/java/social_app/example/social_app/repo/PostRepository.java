package social_app.example.social_app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.Posts;

import java.util.List;
import java.util.Optional;

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
    void incrementLike(@Param("postId") Integer postId);

    @Modifying
    @Query("update Posts  p set p.totalLikes=(p.totalLikes-1) where p.id = :postId")
    void reduceLike(@Param("postId") Integer postId);

    @Query("select p from Posts p where p.id = :postId " +
            "and p.status != 'PRIVATE' " +
            "and (p.status = 'PUBLIC' or " +
            "(p.status = 'FRIENDS_ONLY' and (p.member.id =:memberId or exists (select f.id from FriendShips f " +
            "where (f.requester.id = :memberId and f.addresser.id = p.member.id) " +
            "or (f.addresser.id = :memberId and f.requester.id = p.member.id)))))")
   Optional<Posts> getPost(@Param("postId") Integer postId, @Param("memberId")Integer memberId);

    @Query("select count(l) > 0 from Likes l where l.member.id = :userId and l.post.id = :postId")
    boolean isLiked(@Param("postId") Integer postId, @Param("userId") Integer userId);

    @Query("select p from Posts p where p.member.id = :memberId order by p.createdAt desc ")
    Page<Posts> getMyPosts(@Param("memberId") Integer memberId,Pageable pageable);
}
