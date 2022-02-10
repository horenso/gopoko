package horenso.endpoint;

import horenso.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LobbyEndpoint {
    private WebsocketEntry websocketEntry;
    private LobbyService lobbyService;

    public void joinTable(WebSocketSession session, Map value) {
        return;
    }
}
