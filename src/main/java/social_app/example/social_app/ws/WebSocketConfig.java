package social_app.example.social_app.ws;

import com.sun.security.auth.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import social_app.example.social_app.security.JwtUtil;

import java.security.Principal;
import java.util.Map;

@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private final JwtUtil jwtUtil;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 1. Định nghĩa "Cửa chính" để Client bắt đầu kết nối (Handshake)
        registry.addEndpoint("/ws")
                // Cho phép các domain khác nhau kết nối (CORS)
                .setAllowedOriginPatterns("*")
//              WebSocket — protocol chuẩn, trình duyệt hiện đại đều hỗ trợ
//                  SockJS — thư viện wrapper bọc ngoài WebSocket, nếu trình duyệt không hỗ trợ WebSocket thì
//              SockJS tự động fallback xuống HTTP long-polling
                .addInterceptors(new CustomHandshakeInterceptor())
               .withSockJS();// Phương án dự phòng nếu trình duyệt không hỗ trợ WebSocket
                // nó sẽ dùng bản socket nhưng thấp hơn các trình duyệt đa số điều hiểu
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public @Nullable Message<?> preSend(Message<?> message, MessageChannel channel) {
                log.info("Inbound chanel >>> "+message);

                //accessor check header of stomp
                StompHeaderAccessor accessor = StompHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                assert accessor != null;
                if(StompCommand.CONNECT.equals(accessor.getCommand())){
                    // Lấy token từ header
                  Map session  =  accessor.getSessionAttributes();
                  String token = session != null ? session.get("token").toString() : null;
                    log.info(" Authorization >>> "+token);
                    if(jwtUtil.isValidateToken(token)){
                        // Tạo Principal
                        Principal user = new UserPrincipal(jwtUtil.extractUsername(token));
                        accessor.setUser(user);
                    }
                }
                return message;
            }
        });

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");// chỗ này là nó vào để cho mình kiểm tra trước khi phát
        // có thể check tin nhắn nhạy cảm, check token,...
        registry.enableSimpleBroker("/topic","/queue");// topic thì phát cho tất cả mọi người đăng kí
        //queue cho chat 1-1
        registry.setUserDestinationPrefix("/user");//Cấu hình tiền tố cho tin nhắn riêng (Private Chat)
    }





}
