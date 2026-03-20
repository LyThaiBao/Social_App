package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.FriendShips;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendShipRepository extends JpaRepository<FriendShips,Integer> {
public Optional<FriendShips> findByAddresserIdAndRequesterId(Integer addresser,Integer requester);
}
