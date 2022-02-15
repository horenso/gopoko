package horenso.endpoint.websocket;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import horenso.endpoint.websocket.request.JoinRequest;
import horenso.endpoint.websocket.response.ErrorResponse;
import horenso.endpoint.websocket.response.Response;
import horenso.exceptions.ErrorResponseException;
import horenso.service.WebsocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;

/**
 * This is the entrance were very every Websocket message lands. This class then delegates
 * requests to different endpoints.
 */

@Component
@RequiredArgsConstructor
public class WebsocketEntrance extends TextWebSocketHandler {
    private final LobbyEndpoint lobbyEndpoint;
    private final Gson gson;
    private final WebsocketService websocketService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String jsonString = message.getPayload();
        Map<String, Object> map;
        try {
            map = gson.fromJson(jsonString, Map.class);
        } catch (JsonSyntaxException e) {
            websocketService.sendToOneSession(session, new ErrorResponse("/", "invalid JSON"));
            session.close();
            return;
        }
        System.out.println("<< " + gson.toJson(map));
        System.out.println("Session attributes: " + session.getAttributes());
        String action = map.getOrDefault("action", null).toString();
        String dest = map.getOrDefault("dest", null).toString();
        try {
            if (action == null) {
                throw new ErrorResponseException("/", "field \"action\" is missing", true);
            }
            if (dest == null) {
                throw new ErrorResponseException("/", "field \"dest\" is missing", true);
            }
            switch (action) {
                case "subscribe" -> {
//                    if (dest.equals("table_list")) {
//                        lobbyService.subscribeToTableUpdates(session);
//                    }
//                    if (dest.matches("table_(%d)+")) {
//                        lobbyService.subscribeToTableUpdates(session);
//                    } else {
//                        throw new ErrorResponseException(dest,
//                                String.format("%s is not a valid subscription", dest), true);
//                    }
                }
                case "unsubscribe" -> websocketService.unsubscribe(session, dest);
                case "send" -> handleSend(session, jsonString, dest);
                default -> throw new ErrorResponseException("/", "invalid action", true);
            }
        } catch (ErrorResponseException e) {
            websocketService.sendToOneSession(session, new ErrorResponse(e.getDest(), e.getError()));
            if (e.isDisconnect()) {
                session.close();
            }
        }
    }

    private void handleSend(WebSocketSession session, String jsonString, String dest)
            throws ErrorResponseException {
        switch (dest) {
            case "table_list" -> {
                Response r = lobbyEndpoint.getTableList();
                websocketService.sendToOneSession(session, r);
            }
            case "join" -> {
                Response r = lobbyEndpoint.joinTable(session, gson.fromJson(jsonString, JoinRequest.class));
                websocketService.sendToOneSession(session, r);
            }
            default -> throw new ErrorResponseException("/", String.format("dest \"%s\" invalid", dest), true);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("Connection has been established");
    }
}
