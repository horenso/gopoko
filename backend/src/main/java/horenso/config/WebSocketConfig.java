package horenso.config;

import horenso.endpoint.websocket.WebsocketEntrance;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebsocketEntrance websocketEntrance;
    private final VerifyTokenInterceptor verifyTokenInterceptor;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(websocketEntrance, "/").addInterceptors(verifyTokenInterceptor).setAllowedOrigins("*");
    }
}
