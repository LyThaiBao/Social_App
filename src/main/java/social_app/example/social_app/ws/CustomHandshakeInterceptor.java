package social_app.example.social_app.ws;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.Objects;

@Slf4j
public class CustomHandshakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info(">>>>>>>> LOG HERE");
        if(request instanceof ServletServerHttpRequest servletRequest){ // check and cast
            HttpServletRequest req = servletRequest.getServletRequest();
           Cookie[] cookies  = req.getCookies();
           for (Cookie cookie : cookies){
               if(Objects.equals(cookie.getName(), "accessToken")){
                   log.info(">>>COOKIE: "+cookie);
                   log.info(">>>TOKEN IN COOKIE: "+cookie.getValue());
                   attributes.put("token",cookie.getValue()); // attach to SessionAttributes
               }
           }
        }
        return  true; // true == to next
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, @Nullable Exception exception) {

    }
}
