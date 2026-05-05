package social_app.example.social_app.service.notifi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import social_app.example.social_app.dto.friendship.FriendShipResponse;
import social_app.example.social_app.dto.msg.MessageResponse;
import social_app.example.social_app.dto.notification.FriendRequest;
import social_app.example.social_app.dto.notification.NewMessage;
import social_app.example.social_app.dto.notification.NotificationResponse;
import social_app.example.social_app.entity.Members;
import social_app.example.social_app.entity.Notification;
import social_app.example.social_app.exception.NotFoundResource;
import social_app.example.social_app.mapper.NotificationMapper;
import social_app.example.social_app.repo.NotificationRepository;
import social_app.example.social_app.service.member.MemberService;
import social_app.example.social_app.type.NotificationType;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImp implements NotificationService {
    private final MemberService memberService;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    @Override
    public NotificationResponse<NewMessage> newMessageResponse(MessageResponse messageResponse) {
        return NotificationResponse.<NewMessage>builder()
                .type(NotificationType.NEW_MESSAGE)
                .payload(NewMessage.builder()
                        .content(messageResponse.getContent())
                        .conversationId(messageResponse.getConversationId())
                        .senderId(messageResponse.getSenderId())
                        .build())
                .build();
    }

    @Override
    public NotificationResponse<FriendRequest> friendRequest(FriendShipResponse friendShipResponse) {
        Members recipient = this.memberService.getMemberById(friendShipResponse.getRecipientId());
        Notification notification = Notification.builder()
                .senderId(friendShipResponse.getSenderId())
                .senderName(friendShipResponse.getSenderName())
                .type(NotificationType.REQUEST_FRIEND)
                .recipient(recipient)
                .isRead(false)
                .isDeleted(false)
                .build();
        this.notificationRepository.save(notification);

        return NotificationResponse.<FriendRequest>builder()
                .type(NotificationType.REQUEST_FRIEND)
                .payload(FriendRequest.builder()
                        .senderName(friendShipResponse.getSenderName())
                        .senderId(friendShipResponse.getSenderId())
                        .build())
                        .time(notification.getCreatedAt())
                .build();
    }

    @Override
    public NotificationResponse<?> friendAccepted(FriendShipResponse friendShipResponse) {
        Members recipient = this.memberService.getMemberById(friendShipResponse.getSenderId()); // sender receive notification aft partner accepted
        Members sender = this.memberService.getMemberById(friendShipResponse.getRecipientId());

        Notification notification = Notification.builder()
                .senderId(sender.getId())
                .senderName(sender.getFullName())
                .type(NotificationType.FRIEND_ACCEPTED)
                .recipient(recipient)
                .isRead(false)
                .isDeleted(false)
                .build();
        this.notificationRepository.save(notification);

        return NotificationResponse.<FriendRequest>builder()
                .type(NotificationType.FRIEND_ACCEPTED)
                .payload(FriendRequest.builder()
                        .senderName(sender.getFullName())
                        .senderId(sender.getId())
                        .build())
                .time(notification.getCreatedAt())
                .build();
    }

    @Override
    public List<NotificationResponse<?>> getNotificationByMemberId(Integer recipientId) {
        List<Notification> responseList = this.notificationRepository.findAllByRecipientId(recipientId).stream().filter(n-> !n.isDeleted()).toList();
        log.info(">>>RESPON: "+responseList);
        return responseList.stream().map(this.notificationMapper::convertToNotificationResponse).collect(Collectors.toList());

    }

    @Override
    public Integer countUnreadNotification(Integer recipientId) {
        List<Notification> responseList = this.notificationRepository.findAllByRecipientId(recipientId).stream().filter(n -> !n.isRead()).toList();
        return responseList.size();
    }

    @Override
    public void markRead(Integer recipientId) {
        List<Notification> responseList = this.notificationRepository.findAllByRecipientId(recipientId).stream().filter(n -> !n.isRead()).toList();
        responseList.forEach(n ->{
            n.setRead(true);
        });
        this.notificationRepository.saveAll(responseList);

    }

    @Override
    public Notification deleteNotification(Integer id) {
       Notification notification = this.notificationRepository.findById(id).orElseThrow(()->new NotFoundResource("Not found notification"));
       notification.setDeleted(true);
       this.notificationRepository.save(notification);
      return  notification;
    }


}
