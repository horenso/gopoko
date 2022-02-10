package horenso.endpoint;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import horenso.model.communication.Response;
import horenso.service.LobbyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

/**
 * This is the main entry very every request lands. This class then delegates requests to different endpoints.
 */

@Component
@RequiredArgsConstructor
public class WebsocketEntry extends TextWebSocketHandler {
    private final WebsocketHelper websocketHelper;
    private final LoginEndpoint loginEndpoint;
    private final LobbyEndpoint lobbyEndpoint;
    private final LobbyService lobbyService;
    private final Gson gson;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        System.out.println("in handleTextMessage()");
        Map value;
        try {
            value = gson.fromJson(message.getPayload(), Map.class);
        } catch (JsonSyntaxException e) {
            websocketHelper.sendResponse(new Response("error", "error: invalid JSON"), session);
            session.close();
            return;
        }
        System.out.println("Got valid JSON:" + value.toString());
        System.out.println("Session attributes: " + session.getAttributes());
        String command = value.getOrDefault("dest", null).toString();
        if (command == null) {
            websocketHelper.sendResponse(new Response("error", "error: field 'dest' missing"), session);
            session.close();
            return;
        }
        switch (command) {
            case "login" -> loginEndpoint.login(session, value);
            case "join" -> lobbyEndpoint.joinTable(session, value);
            default -> {
                websocketHelper.sendResponse(
                        new Response("error", String.format("error: dest %s invalid", command)), session);
                session.close();
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection has been established");
        lobbyService.addSession(session);
    }
}
