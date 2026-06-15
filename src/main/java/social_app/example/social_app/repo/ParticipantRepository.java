package social_app.example.social_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import social_app.example.social_app.entity.Conversations;
import social_app.example.social_app.entity.Participants;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participants,Integer> {
    Optional<Participants> findByMemberIdAndConversationId(Integer member_id, Integer conversation_id);
    List<Participants> findByConversationId(Integer conversationId);
    List<Participants> findAllByMemberId(Integer memberId);

    @Modifying
    @Query("delete from Participants p where p.conversation.id = :cvnId and p.member.id = :memberId")
    int deleteByMemberIdAndConversationId(@Param("memberId") Integer memberId, @Param("cvnId") Integer cvnId);

//    @Query("select u.id from Users u where u.id = (select m.user.id from Members m where m.id = (select p.member.id from Participants p where p.conversation.id = :cvnId and p.member.id != :senderId))")
    @Query("select p.member.user.username from Participants p where p.conversation.id =:cvnId and p.member.id != :senderId")
    String getUsernameFromParticipant(@Param("cvnId") Integer cvnId, @Param("senderId") Integer senderId);

    @Query("select p.member.fullName from Participants p where p.conversation.id = :cvnId and p.member.id != :senderId")
    String getFullNameFromParticipant(@Param("cvnId") Integer cvnId,@Param("senderId") Integer senderId);

    @Query("select p1.conversation from  Participants p1 join Participants p2 on p1.conversation.id = p2.conversation.id where p1.member.id =:ownerId and p2.member.id =:partnerId and p1.conversation.type = 'PRIVATE'")
    Conversations isExitsPrivateConv(@Param("ownerId") Integer ownerId, @Param("partnerId") Integer partnerId);

    @Query("select p.conversation from Participants p where p.member.id = :ownerId")
    List<Conversations> getConversations(@Param("ownerId") Integer ownerId);


}
