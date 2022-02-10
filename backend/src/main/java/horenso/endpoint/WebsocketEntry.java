package horenso.endpoint;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import horenso.endpoint.request.JoinRequest;
import horenso.endpoint.request.LoginRequest;
import horenso.endpoint.response.ErrorResponse;
import horenso.endpoint.response.Response;
import horenso.exceptions.ErrorResponseException;
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
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("in handleTextMessage()");
        String jsonString = message.getPayload();
        Map<String, Object> map;
        try {
            map = gson.fromJson(jsonString, Map.class);
        } catch (JsonSyntaxException e) {
            sendResponse(new ErrorResponse("/", "invalid JSON"), session);
            session.close();
            return;
        }
        System.out.println("Got valid JSON:" + map.toString());
        System.out.println("Session attributes: " + session.getAttributes());
        String command = map.getOrDefault("dest", null).toString();
        if (command == null) {
            sendResponse(new ErrorResponse("/", "field \"dest\" is missing"), session);
            session.close();
            return;
        }
        try {
            Response response = switch (command) {
                case "login" -> loginEndpoint.login(session, gson.fromJson(jsonString, LoginRequest.class));
                case "join" -> lobbyEndpoint.joinTable(session, gson.fromJson(jsonString, JoinRequest.class));
                default -> throw new ErrorResponseException("/", String.format("dest \"%s\" invalid", command), true);
            };
            sendResponse(response, session);
        } catch (ErrorResponseException e) {
            sendResponse(new ErrorResponse(e.getDest(), e.getError()), session);
            if (e.isDisconnect()) {
                session.close();
            }
        }
    }

    private void sendResponse(Response response, WebSocketSession session) throws IOException {
        session.sendMessage(new TextMessage(gson.toJson(response)));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection has been established");
        lobbyService.addSession(session);
    }
}
