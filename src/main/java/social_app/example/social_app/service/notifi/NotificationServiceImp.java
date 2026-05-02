//package social_app.example.social_app.service.notifi;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import social_app.example.social_app.dto.friendship.FriendShipResponse;
//import social_app.example.social_app.dto.msg.MessageResponse;
//import social_app.example.social_app.dto.notification.FriendRequest;
//import social_app.example.social_app.dto.notification.NewMessage;
//import social_app.example.social_app.dto.notification.NotificationResponse;
//import social_app.example.social_app.entity.Members;
//import social_app.example.social_app.entity.Notification;
//import social_app.example.social_app.mapper.NotificationMapper;
//import social_app.example.social_app.repo.NotificationRepository;
//import social_app.example.social_app.service.member.MemberService;
//import social_app.example.social_app.type.NotificationType;
//
//import java.util.List;
//
//
//@Service
//@RequiredArgsConstructor
//public class NotificationServiceImp implements NotificationService {
//    private final MemberService memberService;
//    private final NotificationRepository notificationRepository;
//    private final NotificationMapper notificationMapper;
//    @Override
//    public NotificationResponse<NewMessage> newMessageResponse(MessageResponse messageResponse) {
//        return NotificationResponse.<NewMessage>builder()
//                .type(NotificationType.NEW_MESSAGE)
//                .notification()
//                .build();
//    }
//
//    @Override
//    public NotificationResponse<FriendRequest> friendRequest(FriendShipResponse friendShipResponse) {
//        Members recipient = this.memberService.getMemberById(friendShipResponse.getRecipientId());
//        Notification notification = Notification.builder()
//                .senderId(friendShipResponse.getSenderId())
//                .type(NotificationType.REQUEST_FRIEND)
//                .recipient(recipient)
//                .build();
//        this.notificationRepository.save(notification);
//
//        return NotificationResponse.<FriendRequest>builder()
//                .type(NotificationType.REQUEST_FRIEND)
//                .payload(FriendRequest.builder()
//                        .senderName(friendShipResponse.getSenderName())
//                        .senderId(friendShipResponse.getSenderId())
//                        .build())
//                .time(friendShipResponse.getCreatedAt())
//                .build();
//    }
//
//    @Override
//    public List<NotificationResponse<?>> getNotificationByMemberId(Integer recipientId) {
//        List<Notification> responseList = this.notificationRepository.findAllByRecipientId(recipientId).stream().toList();
//
//
//    }
//}
