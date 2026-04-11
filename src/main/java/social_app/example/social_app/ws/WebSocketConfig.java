package social_app.example.social_app.ws;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 1. Định nghĩa "Cửa chính" để Client bắt đầu kết nối (Handshake)
        registry.addEndpoint("/ws")
                // Cho phép các domain khác nhau kết nối (CORS)
                .setAllowedOriginPatterns("*")
//              WebSocket — protocol chuẩn, trình duyệt hiện đại đều hỗ trợ
//                  SockJS — thư viện wrapper bọc ngoài WebSocket, nếu trình duyệt không hỗ trợ WebSocket thì
//              SockJS tự động fallback xuống HTTP long-polling

               .withSockJS();// Phương án dự phòng nếu trình duyệt không hỗ trợ WebSocket
                // nó sẽ dùng bản socket nhưng thấp hơn các trình duyệt đa số điều hiểu
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
