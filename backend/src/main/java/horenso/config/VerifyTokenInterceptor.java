package horenso.config;

import horenso.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class VerifyTokenInterceptor implements HandshakeInterceptor {
    private final UserService userService;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        HttpHeaders headers = request.getHeaders();
        String username = headers.getFirst("username");
        String token = headers.getFirst("token");
        if (username == null || token == null) {
            return false;
        }
        if (!userService.verifyUserToken(username, token)) {
            return false;
        }
        attributes.put("username", username);
        attributes.put("token", token);
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {

    }
}
