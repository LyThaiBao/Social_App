package social_app.example.social_app.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Cấu hình trả về JSON thay vì trang lỗi mặc định của Spring
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Viết thủ công JSON theo format
        String jsonResponse = "{ \"status:\" 4001,\"message:\"Unauthorized,\" body:\"Vui Long Dang Nhap }";
        response.getWriter().write(jsonResponse);
    }
}
