package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.FriendShips;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendShipRepository extends JpaRepository<FriendShips,Integer> {
 @Query("select f from FriendShips f where (f.addresser.id = :addresserId and f.requester.id = :requesterId) or (f.addresser.id = :requesterId and f.requester.id = :addresserId)")
 FriendShips findFriendShip(@Param("requesterId") Integer requesterId,@Param("addresserId") Integer addresserId);


}
