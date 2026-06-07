package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.Members;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, Integer> {
    Optional<Members> getMemberByFullName(String fullName);
    List<Members> getByUserUsername(String userUsername);
    List<Members> getByFullName(String fullName);

    @Query("select s from Members s where s.user.username = :friendName and exists(select 1 from FriendShips f where ((f.requester.user.username =:currentUsername and f.addresser.user.username = :friendName) or (f.requester.user.username =:friendName and f.addresser.user.username = :currentUsername)) and f.status = ACCEPTED)")
    List<Members> searchByUsername(@Param("friendName") String friendName, @Param("currentUsername") String currentUsername);

    @Query("select s from Members s where s.fullName = :friendName and exists(select 1 from FriendShips f where ((f.requester.fullName =:currentName and f.addresser.fullName = :friendName) or (f.requester.fullName =:friendName and f.addresser.fullName = :currentName)) and f.status = ACCEPTED )")
    List<Members> searchByFullName(@Param("friendName") String friendName, @Param("currentName") String currentName);
}
